package com.example.springbatchdownversion.infrastructure.batch.reader.strategies.user;

import com.example.springbatchdownversion.common.constants.DomainType;
import com.example.springbatchdownversion.common.constants.SourceType;
import com.example.springbatchdownversion.domain.User;
import com.example.springbatchdownversion.infrastructure.batch.reader.strategies.ReaderStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MybatisUserReaderStrategy implements ReaderStrategy<User> {

    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public String getDomainType() {
        return DomainType.USER.getKey();
    }

    @Override
    public String getSourceType() {
        return SourceType.MYBATIS.getKey();
    }

    // TODO: 대리님께서 말씀하신 업데이트 시 누락에 주의하여 진행
    @Override
    public ItemReader<User> create() {
        MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<>();

        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.example.springbatchdownversion.infrastructure.mybatis.mappers.UserMapper.selectAllUsers");
        reader.setPageSize(100);

        return reader;
    }
}
