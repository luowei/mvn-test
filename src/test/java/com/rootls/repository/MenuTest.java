package com.rootls.repository;

import com.rootls.model.Menu;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MenuTest {

    Logger logger = LoggerFactory.getLogger(MenuTest.class);

    @Resource
    MenuRepository menuRepository;

    Menu menu1;
    Menu menu2;

    @Before
    public void setUp() throws Exception {
        menu1 = new Menu();
        menu1.setId(1);
        menu1.setName("用户管理");

        menu2 = new Menu();
        menu2.setId(2);
        menu2.setName("人员管理");
        menu2.setUrl("manage/user/list");
    }

    @Test
    public void testFindById() throws Exception {

        Assert.assertTrue(true);
    }

    @Test
    public void testFindByName() throws Exception {


    }

    @Test
    public void testCreateTimeBetween() throws Exception {


    }
}
