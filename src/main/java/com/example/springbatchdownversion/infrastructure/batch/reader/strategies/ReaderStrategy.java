package com.example.springbatchdownversion.infrastructure.batch.reader.strategies;

import com.example.springbatchdownversion.common.constants.DomainType;
import com.example.springbatchdownversion.common.constants.SourceType;
import org.springframework.batch.item.ItemReader;

public interface ReaderStrategy<T> {

    String getDomainType();
    String getSourceType();
    ItemReader<T> create();
}
