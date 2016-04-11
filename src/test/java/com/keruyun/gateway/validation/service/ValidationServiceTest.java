package com.keruyun.gateway.validation.service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tany@shishike.com on 15/12/7.
 */

public class ValidationServiceTest {

    @Test
    public void testValid() throws Exception {

        User user = new User();

        user.setGender(100);
        user.setName("11");

//
//        Name student = new Name();
//        student.setUsername("111");
//        user.setStudent(student);
//
//        // student list
        List<Name> stuList = new ArrayList<Name>();

        Name s1 = new Name();
        s1.setUsername("111");
        stuList.add(s1);

        Name s2 = new Name();
        s2.setUsername("111");
        stuList.add(s2);


        Name s3 = new Name();
        s3.setUsername("1");

        List<Tec> tecList = new ArrayList<Tec>();
        Tec t1 = new Tec();
        t1.setUsername("11");
        tecList.add(t1);
        s3.setTecList(tecList);
        stuList.add(s3);

        user.setStudentList(stuList);

        List<String> errors = new ArrayList<String>();
        ValidationService.valid(user, null, errors);


//        ValidationService.valid();
    }


}



