package com.example.crewstation.country;

import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.mapper.country.CountryMapper;
import com.example.crewstation.repository.country.CountryDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class MapperTests {
    @Autowired
    private CountryMapper countryMapper;

    @Test

    public void testfindAll(){
        //  테스트 자동화 우리 서비스에서 사용하는 태그가 빠진게 없는지 확인
        List<CountryDTO> all = countryMapper.selectAll();
        assertThat(all.size()).isEqualTo(190);

    }
}
