package yasuda.ifpr.edu.com.br.task.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_task")
data class Task(
    var title: String,
    var description: String,
    var done: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    val titleDescription get() = "${title}: ${description}"

    override fun toString() = titleDescription

    override fun equals(other: Any?) =
        when {
            other == null -> false
            id == 0 -> this === other
            else -> id == (other as Task).id
        }




}