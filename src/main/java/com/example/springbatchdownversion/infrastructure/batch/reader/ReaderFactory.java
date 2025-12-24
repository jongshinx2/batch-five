package com.example.springbatchdownversion.infrastructure.batch.reader;

import com.example.springbatchdownversion.common.constants.DomainType;
import com.example.springbatchdownversion.common.constants.SourceType;
import com.example.springbatchdownversion.infrastructure.batch.reader.strategies.ReaderStrategy;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ReaderFactory {

    private record ReaderKey(String domainType, String sourceType) {}

    private final Map<ReaderKey, ReaderStrategy<?>> readerStrategyMap;

    public ReaderFactory(List<ReaderStrategy<?>> readerStrategyList) {
        this.readerStrategyMap = readerStrategyList.stream()
                .collect(Collectors.toMap(
                        r -> new ReaderKey(r.getDomainType(), r.getSourceType()),
                        Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <T> ItemReader<T> get(String domainType, String sourceType) {
        ReaderStrategy<?> strategy = readerStrategyMap.get(new ReaderKey(domainType, sourceType));

        if (strategy == null) {
            throw new IllegalArgumentException("지원하지 않는 소스 타입입니다: " + sourceType);
        }

        return (ItemReader<T>) strategy.create();
    }

}
