package com.example.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.model.ModelUser
import com.example.consumerapp.R
import com.example.consumerapp.databinding.ItemListUserBinding

class AdapterUser: RecyclerView.Adapter<AdapterUser.UsersViewHolder>() {
    private val listView = ArrayList<ModelUser>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack (onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }
    fun setUserList(users:ArrayList<ModelUser>){
        listView.clear()
        listView.addAll(users)
        notifyDataSetChanged()
    }
    inner class UsersViewHolder(val binding: ItemListUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(users : ModelUser){
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClick(users)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(users.avatar_url)
                    .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_account_circle))
                    .into(avatar)
                username.text = users.login
                if (users.type == null){
                    tvType.text = "-"
                }else{
                    tvType.text = users.type
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(listView[position])
    }

    override fun getItemCount(): Int = listView.size

    interface OnItemClickCallBack{
        fun onItemClick(data: ModelUser)
    }
}