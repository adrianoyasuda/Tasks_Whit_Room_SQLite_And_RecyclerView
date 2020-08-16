package yasuda.ifpr.edu.com.br.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import yasuda.ifpr.edu.com.br.task.db.AppDatabase
import yasuda.ifpr.edu.com.br.task.db.dao.TaskDao
import yasuda.ifpr.edu.com.br.task.model.Task
import yasuda.ifpr.edu.com.br.task.adapters.TaskAdapter
import yasuda.ifpr.edu.com.br.task.adapters.TaskAdapterListener

class MainActivity : AppCompatActivity(), TaskAdapterListener {
    private lateinit var taskDao: TaskDao
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "tb_task.db"
            )
                .allowMainThreadQueries()
                .build()
        taskDao = db.taskDao()

        fab_add.setOnClickListener {
            loadData()
            addCard()
        }
        loadData()
    }

    private fun addCard(){
        val xy = adapter.addTask()
        list_people.scrollToPosition(xy)
    }

    private fun loadData() {
        val taski = taskDao.getAll()
        adapter = TaskAdapter(taski.toMutableList(), this)
        list_people.adapter = adapter

        list_people.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }

    override fun taskRemoved(task: Task) {
        taskDao.remove(task)
    }

    override fun taskInsert(task: Task) {
        taskDao.insert(task).toInt()
        list_people.scrollToPosition(adapter.addTaskBD(task))
        loadData()
    }

    override fun taskUpdate(task: Task) {
        taskDao.update(task)
        loadData()
    }
}
