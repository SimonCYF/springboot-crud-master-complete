package com.deloitte.baseapp.commons;

import com.deloitte.baseapp.configs.cache.services.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
public abstract class GenericController<T extends GenericEntity<T>> {

    private final GenericService<T> service;
    private String cacheKey;

    @Autowired
    private RedisService redisService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public GenericController(GenericRepository<T> repository, final String cacheKey) {
        this.cacheKey = cacheKey;
        this.service = new GenericService<T>(repository) {
        };
    }

    @GetMapping("")
    public MessageResponse getAll() {
        final String key = cacheKey + "_" + "getAll";
        try {
            final String redisValue = redisService.getValue(key);

            if (!StringUtils.hasText(redisValue)) {
                final List<T> result = service.getAll();
                redisService.setValue(
                        key,
                        objectMapper.writeValueAsString(result));

                return new MessageResponse<>(service.getAll());
            } else {
                return new MessageResponse<>(objectMapper.readValue(redisValue, List.class), "from redis");
            }

        } catch (JsonProcessingException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }

    @GetMapping("/{id}")
    public MessageResponse getOne(@PathVariable Long id) {
        try {
            final String key = cacheKey + "_" + "getOne_" + id;
            final String redisValue = redisService.getValue(key);

            if (!StringUtils.hasText(redisValue)) {
                final T result = service.get(id);
                redisService.setValue(
                        key,
                        objectMapper.writeValueAsString(result));

                return new MessageResponse<>(result);
            } else {
                return new MessageResponse<>(objectMapper.readValue(redisValue, Object.class), "from redis");
            }

        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        } catch (JsonProcessingException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }

    @PostMapping("/datatable")
    public MessageResponse<Page<T>> getPage(@RequestBody PagingRequest pagingRequest) {
        return new MessageResponse<>(service.getPage(pagingRequest));
    }

    @PostMapping("")
    public MessageResponse create(@RequestBody T created) {
        try {
            return new MessageResponse(service.create(created));
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }

    @PostMapping("/bulk-delete")
    public MessageResponse deleteBulk(@RequestBody CommonRequest.BulkDelete payload) {
        service.deleteBulk(payload.getIds());
        return new MessageResponse(true);
    }

    @PutMapping("/{id}")
    public MessageResponse update(@PathVariable("id") Long id, @RequestBody T updated) {
        try {
            return new MessageResponse(service.update(id, updated));
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable Long id) {
        try {
            service.delete(id);

            return new MessageResponse(true);
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }
    }
}

