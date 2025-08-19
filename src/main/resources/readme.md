2. Модифицируем RecommendationsRepository
   java
   import com.github.benmanes.caffeine.cache.Cache;
   import com.github.benmanes.caffeine.cache.Caffeine;
   import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class RecommendationsRepository {
private final JdbcTemplate jdbcTemplate;

    // Кеш для метода isUserOfProductType
    private final Cache<CacheKey, Boolean> userProductTypeCache = Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.HOURS)
        .maximumSize(10_000)
        .build();
    
    // Кеш для метода sumAmountsForUserAndType
    private final Cache<CacheKey, Long> transactionSumCache = Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.HOURS)
        .maximumSize(10_000)
        .build();

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Ключ для кеша
    private record CacheKey(UUID userId, String arg1, String arg2) {}

    public boolean isUserOfProductType(UUID userId, ProductType productType) {
        CacheKey key = new CacheKey(userId, productType.name(), null);
        return userProductTypeCache.get(key, k -> 
            jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ?)",
                Boolean.class,
                userId,
                productType.name()
            )
        );
    }

    public long sumAmountsForUserAndType(UUID userId, ProductType productType, TransactionType transactionType) {
        CacheKey key = new CacheKey(userId, productType.name(), transactionType.name());
        return transactionSumCache.get(key, k ->
            jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(t.amount), 0) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?",
                Long.class,
                userId,
                productType.name(),
                transactionType.name()
            )
        );
    }
}
3. Проверка работы кеширования
   Добавим логирование в сервис:

java
@Service
public class RecommendationService {
private static final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    public RecommendationResponse getRecommendations(UUID userId) {
        long startTime = System.currentTimeMillis();
        
        List<Recommendation> recommendations = // ... получение рекомендаций
        
        long duration = System.currentTimeMillis() - startTime;
        log.info("Recommendations for user {} fetched in {} ms", userId, duration);
        
        return new RecommendationResponse(userId, recommendations);
    }
}
4. Настройка логирования (application.yml)
   yaml
   logging:
   level:
   org.skypro.teamWork2.service.RecommendationService: DEBUG
   Как это работает:
   При первом запросе данные берутся из БД и сохраняются в кеш

При повторных запросах с теми же параметрами:

Данные возвращаются из кеша

Запрос в БД не выполняется

Кеш автоматически очищается:

Через 1 час после записи

При превышении 10,000 записей (по алгоритму LRU)

private final Cache<CacheKey, Boolean> userProductTypeCache = Caffeine.newBuilder()
.expireAfterWrite(1, TimeUnit.HOURS)  // Кеш на 1 час
.maximumSize(10_000)                 // Максимум 10,000 записей
.build();

// Ключ для кеша (userId + productType)
private record CacheKey(UUID userId, String productType) {}

public boolean isUserOfProductType(UUID userId, ProductType productType) {
CacheKey key = new CacheKey(userId, productType.getDbValue());

    return userProductTypeCache.get(key, k -> {
        String sql = """
            SELECT COUNT(*) > 0
            FROM TRANSACTIONS t
            INNER JOIN products p ON t.product_id = p.id
            WHERE t.user_id = ? AND p.type = ?
            """;
        return jdbcTemplate.queryForObject(sql,
            Boolean.class,
            k.userId(),  // Берем из ключа кеша
            k.productType()
        );
    });
}