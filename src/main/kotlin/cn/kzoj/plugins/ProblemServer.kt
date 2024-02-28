package cn.kzoj.plugins

import cn.kzoj.core.problemserver.ProblemServer
import org.ktorm.database.Database

fun configureProblemServer(database: Database): ProblemServer =
    ProblemServer(database)