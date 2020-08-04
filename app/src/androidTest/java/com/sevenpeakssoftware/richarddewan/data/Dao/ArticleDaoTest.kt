package com.sevenpeakssoftware.richarddewan.data.Dao


import com.sevenpeakssoftware.richarddewan.TestUtils.TestUtil
import com.sevenpeakssoftware.richarddewan.data.db.DatabaseServiceTest
import junit.framework.Assert.assertEquals
import org.junit.Test



/*
created by Richard Dewan 12/07/2020
*/
class ArticleDaoTest: DatabaseServiceTest() {

    @Test
    fun insert_read_update(){
        val articleEntity = TestUtil.articleEntity
        val articleEntity2 = TestUtil.articleEntity2

        //insert
        val result =  articleDao.insert(articleEntity)
        assertEquals(1L,result)
        println("insert  $result")

        //read by id
        val articleList = articleDao.getArticles(result)
        assertEquals(articleEntity.title, articleList[0].title)
        println("read  by id  $articleList")

        //read all
        val articleLists = articleDao.getArticles().blockingGet()
        assertEquals(1,articleLists.size)
        println("read all result $articleLists")

        //update
        val updateResult  = articleDao.updateArticle(
            articleLists[0].articleId,
            "test title",
            "test ingress",
            "test image"
            )
        assertEquals(1,updateResult)
        println("update $updateResult")

        // after update read and verify
        val readAll = articleDao.getArticles().blockingGet()
        assertEquals("test title",readAll[0].title)
        println("after update read and verify $readAll")

        //insert or update .. insert record return row id 2
        val insertOrUpdateResult = articleDao.insertOrUpdate(articleEntity2).blockingGet()
        assertEquals(2L,insertOrUpdateResult)

        //read after insert .. should return 2 record
        val lists = articleDao.getArticles().blockingGet()
        assertEquals(2,lists.size)
        println("read after insert .. should return 2 record $lists")


    }

}