package com.redislabs.edu.redi2read.repositories;

import com.redislabs.edu.redi2read.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.redislabs.modules.rejson.JReJSON;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CartRepository implements CrudRepository<Cart, String> {

    /**
     * RedisJSON is not yet seamlessly integrated with Spring,
     * but that does not prevent us from using RedisJSON the “Spring Way”.
     * We have provided an implementation of Spring’s CrudRepository
     * so that we can implement our services and controllers.
     */

    private JReJSON redisJson = new JReJSON();
    private final static String idPrefix = Cart.class.getName();

    @Autowired
    private RedisTemplate<String, String> template;

    private SetOperations<String, String> redisSets() {
        return template.opsForSet();
    }
    private HashOperations<String, String, String> redisHash() {
        return template.opsForHash();
    }

    /**
     * a collection of Redis JSON objects
     * and a Redis Set to maintain the collection of keys.
     */
    @Override
    public <S extends Cart> S save(S cart) {
        // set cart id
        if (cart.getId() == null) {
            cart.setId(UUID.randomUUID().toString());
        }
        String key = getKey(cart);
        redisJson.set(key, cart);
        redisSets().add(idPrefix, key);
        redisHash().put("carts-by-user-id-idx", cart.getUserId(), cart.getId());
        return cart;
    }

    @Override
    public <S extends Cart> Iterable<S> saveAll(Iterable<S> carts) {
        return StreamSupport
                             .stream(carts.spliterator(), false)
                             .map(cart -> save(cart))
                             .collect(Collectors.toList());
    }
    @Override
    public Optional<Cart> findById(String id) {
        Cart cart = redisJson.get(getKey(id), Cart.class);
        return Optional.ofNullable(cart);
    }
    @Override
    public boolean existsById(String id) {
        return template.hasKey(getKey(id));
    }
    @Override
    public Iterable<Cart> findAll() {
        String[] keys = redisSets().members(idPrefix).stream().toArray(String[]::new);
        return (Iterable<Cart>) redisJson.mget(Cart.class, keys);
    }
    @Override
    public Iterable<Cart> findAllById(Iterable<String> ids) {
        String[] keys = StreamSupport.stream(ids.spliterator(), false) //
                                     .map(id -> getKey(id)).toArray(String[]::new);
        return (Iterable<Cart>) redisJson.mget(Cart.class, keys);
    }
    @Override
    public long count() {
        return redisSets().size(idPrefix);
    }

    @Override
    public void deleteById(String id) {
        redisJson.del(getKey(id));
    }
    @Override
    public void delete(Cart cart) {
        deleteById(cart.getId());
    }
    @Override
    public void deleteAll(Iterable<? extends Cart> carts) {
        List<String> keys = StreamSupport
                                          .stream(carts.spliterator(), false)
                                          .map(cart -> idPrefix + cart.getId())
                                          .collect(Collectors.toList());
        redisSets().getOperations().delete(keys);
    }
    @Override
    public void deleteAll() {
        redisSets().getOperations().delete(redisSets().members(idPrefix));
    }
    public Optional<Cart> findByUserId(Long id) {
        String cartId = redisHash().get("carts-by-user-id-idx", id.toString());
        return (cartId != null) ? findById(cartId) : Optional.empty();
    }
    public static String getKey(Cart cart) {
        return String.format("%s:%s", idPrefix, cart.getId());
    }
    public static String getKey(String id) {
        return String.format("%s:%s", idPrefix, id);
    }

}
