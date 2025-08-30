1. Исправьте SQL-запрос в RecommendationsRepository
   java
   public UUID getUserIdByNameAndLastName(String name, String lastName) {
   String sql = "SELECT id FROM USERS u WHERE u.first_name = ? AND u.last_name = ?";
   logger.debug("Searching user: firstName='{}', lastName='{}'", name, lastName);

   try {
   UUID result = jdbcTemplate.queryForObject(sql, UUID.class, name, lastName);
   logger.debug("User found: {}", result);
   return result;
   } catch (Exception e) {
   logger.debug("User not found: {} {} - {}", name, lastName, e.getMessage());
   return null;
   }
   }
