package com.viswa.hobbyapp_160421069.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.viswa.hobbyapp_160421069.databinding.MangaListItemBinding
import com.viswa.hobbyapp_160421069.model.Blog
import java.lang.Exception

class MangaListAdapter(val mangaList: ArrayList<Blog>):RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>() {
    class MangaViewHolder (var binding: MangaListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val binding = MangaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MangaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        holder.binding.txtTitle.text = mangaList[position].title
        holder.binding.txtUser.text = mangaList[position].writer
        holder.binding.txtSummary.text = mangaList[position].smr

        holder.binding.btnRead.setOnClickListener{
            val mangaId = mangaList[position].id
            val action = HomeFragmentDirections.actionReadFragment(mangaId)
            Navigation.findNavController(it).navigate(action)
        }

        Log.d("picasso", mangaList[position].picUrl.toString() )

        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener { _, _, exception ->
            Log.e("picasso error", exception.printStackTrace().toString())
            exception.printStackTrace()
        }
        picasso.build().load(mangaList[position].picUrl.toString()).into(holder.binding.imgBlog, object:
            Callback {
            override fun onSuccess() {
                Log.d("picasso", "success")
                holder.binding.imgBlog.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                Log.e("picasso error", e.toString())
            }

        })
    }
    fun updateMangaList(newMangaList:ArrayList<Blog>){
        mangaList.clear()
        mangaList.addAll(newMangaList)
        notifyDataSetChanged()
    }
}