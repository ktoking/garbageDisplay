package com.fehead.testproject.test;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fehead.testproject.dao.LabelDOMapper;
import com.fehead.testproject.dao.UserDOMapper;
import com.fehead.testproject.dataobject.LabelDO;
import com.fehead.testproject.dataobject.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private LabelDOMapper labelDOMapper;

    @Test
    public void select(){
        List<UserDO> userDOList=userDOMapper.selectList(null);
    }

    /**
     * 插入信息
     */
    @Test
    public void insert(){
        UserDO userDO=new UserDO();
        userDO.setId(5);
        userDO.setUserName("张倩111");
        userDO.setUserPhone("1312312312312");
        userDO.setUserAge(18);
        userDO.setUserEmail("sdsadas@qq.com");
        int row=userDOMapper.insert(userDO);
        System.out.println("影响了"+row+"行");
    }

    /**
     * selectmap查询叫张倩的用户信息
     */
    @Test
    public void selectMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("user_name","张倩");
        List<UserDO> userDOList=userDOMapper.selectByMap(map);
    }

    //条件构造器,查询user表中,user_name有张且年龄在20-30之间,且email不为空的用户
    @Test
    public void selectByWrapper(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("user_name","张").between("user_age",20,30).isNotNull("user_email");
        List<UserDO> userDOList=userDOMapper.selectList(queryWrapper);
    }

    //自己的Label测试
    @Test
    public void TestLabel(){
//        取出逗号之前的字符串
      String s="sdada";
//        String str1=s.substring(0, s.indexOf(","));
//        System.out.println(str1);

        String[] result=s.split(",");
        System.out.println(result[0]);
        QueryWrapper<LabelDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("label","0");
        LabelDO labelDO=labelDOMapper.selectOne(queryWrapper);
    }


    //条件构造器,查询user表中user_name字段模糊查询'张',或者user_age年龄大于20岁,按照user_age降序排列,年龄相同的就按照id升序排列
    @Test
    public void select_1_ByWrapper(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("user_name","张").or().ge("user_age",20).orderByDesc("user_age").orderByAsc("id");
        List<UserDO> userDOList=userDOMapper.selectList(queryWrapper);
    }

    //条件构造器,查询user表中姓名有张,或者年龄小于30大于18且email不为空的用户
    @Test
    public void select_2_ByWrapper(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.likeRight("user_name","张").or(wq->wq.lt("user_age",30).gt("user_age",18).isNotNull("user_email"));
        List<UserDO> userDOList=userDOMapper.selectList(queryWrapper);
    }

    //查询(年龄小于30或者email不为空)且名字有张的用户信息
    @Test
    public void select_3_ByWrapper(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.nested(qw->qw.lt("user_age",30).or().isNotNull("user_email")).likeRight("user_name","张");
        List<UserDO> userDOList=userDOMapper.selectList(queryWrapper);
    }

    //查询指定字段并且用户年龄在指定年龄中
    @Test
    public void select_4_ByWrapper(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("user_name","user_age","user_email").in("user_age", Arrays.asList(20,6,30,15));
        List<UserDO> userDOList=userDOMapper.selectList(queryWrapper);
    }

    //查询不为空的字段加进查询子句中
    @Test
    public void select_5_ByWrapper(){
        String name="王";
        String age="";
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),"user_name",name).like(StringUtils.isNotEmpty(age),"user_age",age);
        List<UserDO> userDOList=userDOMapper.selectList(queryWrapper);
    }

    /**
     * 通过实体类查询实体类有的字段的信息
     */
    @Test
    public void select_6_ByWrapper(){
        UserDO userDO=new UserDO();
        userDO.setUserAge(20);
        userDO.setUserName("张倩");
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>(userDO);
        List<UserDO> userDOList=userDOMapper.selectList(queryWrapper);
    }

    /**
     * 通过selectMap的方式返回一个map查询你想要的字段信息
     */
    @Test
    public void select_7_ByWrapperByMaps(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("user_name","user_phone").like("user_name","张").eq("user_age",18);
        List<Map<String,Object>> userDOList=userDOMapper.selectMaps(queryWrapper);
    }

    /**
     * 查询在查询条件下的返回记录数
     */
    @Test
    public void select_8_ByWrapperCount(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("user_name","张").le("user_age",30);
        Integer count=userDOMapper.selectCount(queryWrapper);
        System.out.println("总记录数: "+count);
    }

    /**
     * lambda表达式拼接语句防误写
     */
    @Test
    public void select_9_Lambda(){
        LambdaQueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>().lambda();
        queryWrapper.select(UserDO::getId).like(UserDO::getUserName,"张").le(UserDO::getUserAge,30);
        List<UserDO> list=userDOMapper.selectList(queryWrapper);
    }

    /**
     * 实现分页查询查询在条件下若每页显示2条记录,显示第一页的记录
     */
    @Test
    public void select_10_Page(){
        QueryWrapper<UserDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.le("user_age",30);

        Page<UserDO> page=new Page<>(1,2);

        IPage<UserDO> iPage=userDOMapper.selectPage(page,queryWrapper);
        System.out.println("总页数: "+iPage.getPages());
        System.out.println("总记录数: "+iPage.getTotal());
        List<UserDO> records = iPage.getRecords();
//   引用输出     records.forEach(System.out::println);
        records.forEach(l-> System.out.println(l));
    }

    /**
     * 根据id更新用户信息
     */
    @Test
    public void select_11_updateById(){
        UserDO userDO=new UserDO();
        userDO.setId(4);
        userDO.setUserAge(25);
        userDO.setUserEmail("aikaikai@qq.com");
        int rows=userDOMapper.updateById(userDO);
        System.out.println("影响了: "+rows+" 行");
    }

    /**
     * 更新用户年龄和邮箱被更新用户信息为张倩,邮箱为qianqian@qq.com
     */
    @Test
    public void select_12_update(){
        UpdateWrapper<UserDO> userDOUpdateWrapper=new UpdateWrapper<>();
        userDOUpdateWrapper.eq("user_name","张倩").eq("user_email","qianqian@qq.com");

        UserDO userDO=new UserDO();
        userDO.setUserAge(26);
        userDO.setUserEmail("aiqianqian@qq.com");
        int rows=userDOMapper.update(userDO,userDOUpdateWrapper);
        System.out.println("影响记录数: "+rows);
    }

    /**
     * 当更新少数字段的时候可以用set方法
     */
    @Test
    public void select_13_update(){
        UpdateWrapper<UserDO> userDOUpdateWrapper=new UpdateWrapper<>();
        userDOUpdateWrapper.eq("user_name","张倩").eq("user_email","aiqianqian@qq.com").set("user_age",27);

        int rows=userDOMapper.update(null,userDOUpdateWrapper);
        System.out.println("影响记录数: "+rows);
    }

    /**
     * Lambda表达式更新用户信息
     */
    @Test
    public void select_14_updateLambda(){
        LambdaUpdateWrapper<UserDO> updateWrapper = new UpdateWrapper<UserDO>().lambda();
        updateWrapper.eq(UserDO::getUserName,"张倩").eq(UserDO::getUserAge,27).set(UserDO::getUserEmail,"aiqianqian1@qq.com");

        int rows=userDOMapper.update(null,updateWrapper);
        System.out.println("影响记录数: "+rows);
    }

    /**
     * 根据id删除信息返回删除条数
     */
    @Test
    public void select_15_deleteById(){
        int rows=userDOMapper.deleteById(6);
        System.out.println("删除条数 :"+rows+" 条");
    }

    /**
     * 根据map设置进去的参数删除符合条件的记录
     */
    @Test
    public void select_16_deleteByMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("user_name","测试");
        map.put("user_age",25);
        int rows=userDOMapper.deleteByMap(map);
        System.out.println("删除条数 :"+rows+" 条");

        // 下一个方法是批量删除id为list里的记录,返回删除记录数
        // int row=userDOMapper.deleteBatchIds(Arrays.asList(7));
    }

    /**
     * Lambda表达式拼接删除语句
     */
    @Test
    public void select_17_deleteLambda(){
        LambdaQueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>().lambda();
        queryWrapper.eq(UserDO::getUserName,"测试").eq(UserDO::getUserAge,15);
        int rows=userDOMapper.delete(queryWrapper);
        System.out.println("删除条数 :"+rows+" 条");
    }

    @Test
    public void tuxiang(){

    }



}
