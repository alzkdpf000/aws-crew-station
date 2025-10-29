package com.example.crewstation.country;


import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.mapper.country.CountryMapper;
import com.example.crewstation.repository.country.CountryDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
public class DAOTests {
    @Autowired
    private CountryDAO countryDAO;

    @Test
    public void testfindAll(){
        //  테스트 자동화 우리 서비스에서 사용하는 태그가 빠진게 없는지 확인
        List<CountryDTO> all = countryDAO.findAll();
        assertThat(all.size()).isEqualTo(190);

    }
}
