package cn.kzoj.data.user

import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.text

object Users : Table<User>("user") {
    val id = int(UserTableColumn.ID).primaryKey().bindTo { it.id }
    val username = text(UserTableColumn.USERNAME).bindTo { it.username }
    val real_name = text(UserTableColumn.REAL_NAME).bindTo { it.realName }
    val gender = text(UserTableColumn.GENDER).bindTo { it.gender }
    val school = text(UserTableColumn.SCHOOL).bindTo { it.school }
    val graduation_year = text(UserTableColumn.GRADUATION_YEAR).bindTo { it.graduationYear }
    val github_page = text(UserTableColumn.GITHUB_PAGE).bindTo { it.githubPage }
    val email_address = text(UserTableColumn.EMAIL_ADDRESS).bindTo { it.emailAddress }
    val avatar_url = text(UserTableColumn.AVATAR_URL).bindTo { it.avatarUrl }
    val title_name = text(UserTableColumn.TITLE_NAME).bindTo { it.titleName }
    val online_status = int(UserTableColumn.ONLINE_STATUS).bindTo { it.onlineStatus }
    val passwd = text(UserTableColumn.PASSWD).bindTo { it.passwd }
    val encrypt_func = text(UserTableColumn.ENCRYPT_FUNC).bindTo { it.passwd }
    val public_key = text(UserTableColumn.PUBLIC_KEY).bindTo { it.publicKey }
    val private_key = text(UserTableColumn.PRIVATE_KEY).bindTo { it.privateKey }
    val date_created = datetime(UserTableColumn.DATE_CREATED).bindTo { it.dateCreated }
    val date_last_modified = datetime(UserTableColumn.DATE_LAST_MODIFIED).bindTo { it.dateLastModified }
}

object UserTableColumn {
    const val ID = "id"
    const val USERNAME = "username"
    const val REAL_NAME = "real_name"
    const val GENDER = "gender"
    const val SCHOOL = "school"
    const val GRADUATION_YEAR = "graduation_year"
    const val GITHUB_PAGE = "github_page"
    const val EMAIL_ADDRESS = "email_address"
    const val AVATAR_URL = "avatar_url"
    const val TITLE_NAME = "title_name"
    const val ONLINE_STATUS = "online_status"
    const val PASSWD = "passwd"
    const val ENCRYPT_FUNC = "encrypt_func"
    const val PUBLIC_KEY = "public_key"
    const val PRIVATE_KEY = "private_key"
    const val DATE_CREATED = "date_created"
    const val DATE_LAST_MODIFIED = "date_last_modified"
}