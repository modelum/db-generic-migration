SELECT COUNT(*)
FROM app_user 
WHERE user_id 
           IN (SELECT user_id 
                  FROM listening 
                  WHERE plays_count = 10
                  GROUP BY user_id 
                  HAVING COUNT(*) > 4);
