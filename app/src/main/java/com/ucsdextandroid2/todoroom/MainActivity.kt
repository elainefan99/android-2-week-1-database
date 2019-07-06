package com.ucsdextandroid2.todoroom

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.paging.toLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.am_recycler_view)
        val adapter = NotesAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recyclerView.adapter = adapter
        adapter.onNoteClickListener = { note > startActivity(NoteActivity.createIntent(this, note)) }
      adapter.registerAdapterDataObserver(object: Recyclerview.AdapterDataObserver(){
          override fun onItemRangeInserted(positionStart: Int, itemCount: Int){
              super.onItemRangeInserted(positionStart, itemCount)

              if(positionStart == 0)
                  recyclerView.layoutManager?.scrollToPosition(position: 0)

          }
      })

        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.START or ItemTouchHelper.END))
            itemTouchHelper.attachToRecyclerView(recyclerView)
        override fun onMove(recyclerView: RecylerView.ViewHolder, direction: Int)
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val note = adapter.removeItem(viewHolder.adapterPosition)
    }

        if(note != null)
            AppDatabase.get(this@MainActivity).noteDao().deleteNote(note)
        val addNoteView: View = findViewById(R.id.am_add_note)
        addNoteView.setOnClickListener{
            startActivityForResult(NoteActivity.createIntent(this), 7)

            adapter.submitList(notes)
    }
  onDataChanged()
        AppDatabase.get(this).noteDao().getAllNotesLiveData().observe(this), observer<list<note>>{
          notes
            private fun toastforNotes(){
                val notes = AppDatabase.get(this).noteDao().getAllNotes()
                notes.forEach{
                    Log.d("MainActivity", it.title)

                }
                Toast.makeText(this, "total Number of Notes: " + notes.size, Toast.LENGTH_SHORT).show()
        })

}
private fun toastforNotes(){
    val notes = AppDatabase.get(this).noteDao().getAllNotes()
    notes.forEach{
        Log.d("MainActivity", it.title)

    }
    Toast.makeText(this, "total Number of Notes: " + notes.size, Toast.LENGTH_SHORT).show()
}
    override fun onActivityTrsult(requestcode: Int, ResultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
    }

}

 private NotesAdapter : ListAdapter<Note, NoteCardViewHolder>(listDiffer){
        var onNoteClickListener: ((Note)-> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteCardViewHolder{
        vak viewHolder = NorecardviewHolder.inflate(parent)
        viewHolder.itemView.setOnClickListener{
            val item = get Item(viewHolder.adapterPosition)
            if(item!= null){
                onNoteclickListener?.invoke(item)

            }
        }
        return NoteCardViewHolder.inflate(parent)
    }
        override fun OnbindViewHolder(holder: NoteCardViewHolder, position: Int)
    companion object{
        val listDiffer= DiffUtil.Itemcallback<Note>=object:DiffUtil.ItemCallBack<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem:note): Boolean {
                return oldItem.datetime == newItem.datetime

            }
            override fun areContentsTheSame(oldItem: Note, newItem:Note): Boolean{
                return oldItem == newItem
            }
        }
    }
}

}

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

}

private class NoteCardViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    val image: ImageView = itemView.findViewById(R.id.vnc_image)
    val titleView: TextView = itemView.findViewById(R.id.vnc_title)
    val textView: TextView = itemView.findViewById(R.id.vnc_text)

    companion object {
        fun inflate(parent: ViewGroup): NoteCardViewHolder = NoteCardViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_note_card, parent, false)
        )
    }

    fun bind(note: Note?) {
if(note !=null){
    titleView.setText(note.title)
    textView.setText=note.text

    if(note.imageUri !=null) {
        image.isVisible= true
        image.setImageURI(note.imageUri)

    }
    else{
        image.isVisible=false
    }
}
        else{
            titleView.text=""
    textview.text=""
    image.isvisible=false
        }
        fun removeItem(position:Int): Note?{
            val note = getItem(position)


            return note
        }

    }

}