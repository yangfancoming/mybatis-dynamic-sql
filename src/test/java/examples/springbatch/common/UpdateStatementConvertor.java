
package examples.springbatch.common;

import static examples.springbatch.mapper.PersonDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatementConvertor implements Converter<Person, UpdateStatementProvider> {

    @Override
    public UpdateStatementProvider convert(Person source) {
        return UpdateDSL.update(person)
                .set(firstName).equalTo(source::getFirstName)
                .set(lastName).equalTo(source::getLastName)
                .where(id, isEqualTo(source::getId))
                .build()
                .render(RenderingStrategy.MYBATIS3);
    }
}
