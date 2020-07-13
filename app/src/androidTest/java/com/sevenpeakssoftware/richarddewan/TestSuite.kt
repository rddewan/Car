package com.sevenpeakssoftware.richarddewan

import com.sevenpeakssoftware.richarddewan.data.Dao.ArticleDaoTest
import com.sevenpeakssoftware.richarddewan.ui.main.MainActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    ArticleDaoTest::class
)
class TestSuite