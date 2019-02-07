package ru.otus.library.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.util.ReflectionUtils;
import ru.otus.library.annotations.CascadeDelete;
import ru.otus.library.annotations.CascadeSave;

import java.lang.reflect.Field;
import java.util.Objects;

public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
                final Object fieldValue = field.get(source);
                DbRefFieldCallback callback = new DbRefFieldCallback();
                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                if (!callback.isIdFound()) {
                    throw new MappingException("Can't perform cascade save on child object without id set");
                }
                mongoOperations.save(fieldValue);
            }
        });
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeDelete.class)) {
                final Object fieldValue = field.get(source);
                if (Objects.isNull(fieldValue)) {
                    return;
                }
                DbRefFieldCallback callback = new DbRefFieldCallback();
                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                if (!callback.isIdFound()) {
                    throw new MappingException("Can't perform cascade delete on child object without id set");
                }
                mongoOperations.remove(fieldValue);
            }
        });
    }

    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        @Override
        public void doWith(Field field) throws IllegalArgumentException {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        boolean isIdFound() {
            return idFound;
        }
    }

}
