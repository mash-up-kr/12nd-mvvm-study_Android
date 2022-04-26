package com.example.mvvmstudy.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Callback
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmstudy.R
import com.example.mvvmstudy.IO.RetrofitClass
import com.example.mvvmstudy.data.Repositories
import com.example.mvvmstudy.databinding.ActivityMainBinding
import org.jetbrains.anko.indeterminateProgressDialog
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var page = 0
    private var search =""

    private var _binding : ActivityMainBinding?= null
    private val binding get() = _binding!!
    private val adapter = RepositoryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.listRepo.adapter = adapter
        binding.listRepo.layoutManager = LinearLayoutManager(this)

        initScrollListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val searchItem = menu?.findItem(R.id.item_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search.."

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p: String?): Boolean {
                if(search != p.toString()) page = 0
               fetching(p.toString())
                search = p.toString()

                // 요녀석 땜에 꽤 고생
                searchView.clearFocus()
               return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }
    }


    fun fetching(q: String){
        RetrofitClass.retrofitService
            .searchRepositories(q,getPage(),50)
            .enqueue(object: Callback<Repositories>{
                val progress = indeterminateProgressDialog(message = "Loading...")
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<Repositories>,
                    response: Response<Repositories>
                ) {
                    progress.show()
                    when(response!!.code()){
                        200->{
                            val body = response.body() as Repositories
                            adapter.setRepository(body.repositories)
                            Log.d("api","Ok")
                            progress.cancel()
                        }
                        304->{
                            Log.d("api","Not modified")
                            progress.cancel()
                        }
                        422 ->{
                            Log.d("api","Validation failed")
                            progress.cancel()
                        }
                        503->{
                            Log.d("api","Service unavailable")
                            progress.cancel()
                        }
                    }
                }

                override fun onFailure(call: Call<Repositories>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
    // 복붙코드 ㅎㅎ..
    fun fetchingMore(q:String){
        val progress = indeterminateProgressDialog(message = "Loading...")
        RetrofitClass.retrofitService
            .searchRepositories(q,getPage(),50)
            .enqueue(object : Callback<Repositories>{
                override fun onResponse(
                    call: Call<Repositories>,
                    response: Response<Repositories>
                ) {
                    progress.show()
                    when(response!!.code()){
                        200->{
                            val body = response.body() as Repositories
                            adapter.run{
                                adapter.addRepository(body.repositories)
                            }
                            Log.d("api","Ok")
                            progress.cancel()
                        }
                        304->{
                            Log.d("api","Not modified")
                            progress.cancel()
                        }
                        422 ->{
                            Log.d("api","Validation failed")
                            progress.cancel()
                        }
                        503->{
                            Log.d("api","Service unavailable")
                            progress.cancel()
                        }
                    }
                }
                override fun onFailure(call: Call<Repositories>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun initScrollListener(){
        binding.listRepo.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1
                // 스크롤이 끝에 도달했는지 확인
                if (!binding.listRepo.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    fetchingMore(search)
                }
            }
        })
    }
    private fun getPage():Int{
        page++
        Log.d("getPage",page.toString())
        return page
    }

}