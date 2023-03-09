package org.springframework.springboot.lab.mongodb.mongo;

import cn.hutool.core.lang.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;


/**
 * @author K
 */
@Component
public class MongoInsertEventListener extends AbstractMongoEventListener<IncIdEntity> {

    /**
     * sequence - 集合名
     */
    private static final String SEQUENCE_COLLECTION_NAME = "sequence";

    /**
     * sequence - 自增值的字段名
     */
    private static final String SEQUENCE_FIELD_VALUE = "value";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<IncIdEntity> event) {
        IncIdEntity entity = event.getSource();
        // 判断 id 为空
        if (entity.getId() == null) {
            // 获取下一个编号
            Number id = this.getNextId(entity);
            entity.setId(id);
        }
    }

    /**
     * 获取实体的下一个主键 ID 编号
     *
     * @param entity 实体
     * @return id
     */
    private Number getNextId(IncIdEntity entity) {
        // 使用实体名的简单类名, 作为 ID 编号
        String id = entity.getClass().getSimpleName();
        // 创建 Query 对象
        Query query = new Query(Criteria.where("_id").is(id));
        // 创建 Update 对象
        Update update = new Update();
        // 自增值
        update.inc(SEQUENCE_FIELD_VALUE, 1);
        // 创建 FindAndModifyOptions 对象
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);// 如果不存在则进行插入
        options.returnNew(true); // 返回新值
        // 执行操作
        HashMap<String, Object> result = mongoTemplate.findAndModify(query, update, options, HashMap.class, SEQUENCE_COLLECTION_NAME);
        // 返回主键
        return (Number) result.get(SEQUENCE_FIELD_VALUE);
    }
}
