package tdc.edu.vn.project_mobile_be.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/set")
    public String setKey(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Key set successfully";
    }

    @GetMapping("/get")
    public String getKey(@RequestParam String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
