package codeit.apps.basicassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


val supabaseClient = createSupabaseClient(
    supabaseUrl = "https://sqrqxqlulfmmtktqxfsb.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNxcnF4cWx1bGZtbXRrdHF4ZnNiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTQ5NzUxNjcsImV4cCI6MjAzMDU1MTE2N30.jtjGNUI1MtcSsXAPSdSbDuRyOTsLk-JNvtelX0kTuYk"
) {
    install(Postgrest)
    //install other modules
}

class MainActivity : AppCompatActivity(), VideoAdapter.OnItemClickListener {
    private val videos = mutableListOf<videos>()
    private lateinit var adapter: VideoAdapter
    private lateinit var searchView: SearchView
    private val filteredVideos = mutableListOf<videos>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainRV = findViewById<RecyclerView>(R.id.mainRV)
        searchView = findViewById(R.id.searchBar)


        val initialvideos = listOf(
            videos(1, "https://sqrqxqlulfmmtktqxfsb.supabase.co/storage/v1/object/sign/sampleVideos/3195394-uhd_3840_2160_25fps.mp4?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJzYW1wbGVWaWRlb3MvMzE5NTM5NC11aGRfMzg0MF8yMTYwXzI1ZnBzLm1wNCIsImlhdCI6MTcxNDk3ODMxMSwiZXhwIjoxNzE3NTcwMzExfQ.WoFp-D4G6Xtu7IxfO-qYnCeE42DvyGmiwnWUwoP2nYg&t=2024-05-06T01%3A21%3A46.675Z","MrBeast", "Throwing money", "Giving away 500 dollars",100),
            videos(2, "https://sqrqxqlulfmmtktqxfsb.supabase.co/storage/v1/object/sign/sampleVideos/3209828-uhd_3840_2160_25fps.mp4?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJzYW1wbGVWaWRlb3MvMzIwOTgyOC11aGRfMzg0MF8yMTYwXzI1ZnBzLm1wNCIsImlhdCI6MTcxNDk3ODM5MSwiZXhwIjoxNzE3NTcwMzkxfQ.VfhY7eFOjQWenTESfk99s6Cv5QmPrKaq-6OdE2V8bAk&t=2024-05-06T01%3A23%3A06.401Z","Phillip Lackener", "Jet spray", "Channel B",500),
            videos(3, "https://sqrqxqlulfmmtktqxfsb.supabase.co/storage/v1/object/sign/sampleVideos/3195394-uhd_3840_2160_25fps.mp4?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJzYW1wbGVWaWRlb3MvMzE5NTM5NC11aGRfMzg0MF8yMTYwXzI1ZnBzLm1wNCIsImlhdCI6MTcxNDk3ODMxMSwiZXhwIjoxNzE3NTcwMzExfQ.WoFp-D4G6Xtu7IxfO-qYnCeE42DvyGmiwnWUwoP2nYg&t=2024-05-06T01%3A21%3A46.675Z","Papaya", "video games", "Channel A",180),
            videos(4, "https://sqrqxqlulfmmtktqxfsb.supabase.co/storage/v1/object/sign/sampleVideos/3209828-uhd_3840_2160_25fps.mp4?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJzYW1wbGVWaWRlb3MvMzIwOTgyOC11aGRfMzg0MF8yMTYwXzI1ZnBzLm1wNCIsImlhdCI6MTcxNDk3ODM5MSwiZXhwIjoxNzE3NTcwMzkxfQ.VfhY7eFOjQWenTESfk99s6Cv5QmPrKaq-6OdE2V8bAk&t=2024-05-06T01%3A23%3A06.401Z","JOSH", "microscope", "Channel B",840),

            )

        videos.addAll(initialvideos);

//        GlobalScope.launch(Dispatchers.Main) {
//            fetchVideosFromSupabase()
//        }

        adapter  = VideoAdapter(this, videos, this)
        mainRV.layoutManager = LinearLayoutManager(this)
        mainRV.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {


                newText?.let {
                    filterVideos(it)
                }
                return true
            }
        })
        adapter.notifyDataSetChanged()

    }

    private fun filterVideos(query: String) {
        filteredVideos.clear()
        if (query.isEmpty()) {
            Log.e("priyanshu", "1")
            filteredVideos.addAll(videos)
        } else {

            val lowerCaseQuery = query.trim().lowercase()
            videos.forEach { video ->
                Log.e("priyanshu1", video.title);
                if (video.title.lowercase().contains(lowerCaseQuery)) {
                    filteredVideos.add(video)
                }
            }
            Log.e("priyanshu", lowerCaseQuery);
        }
        adapter.videos = filteredVideos;
        adapter.notifyDataSetChanged()
    }
    override fun onItemClick(video: videos) {
        // Handle item click here
        // For example, open VideoPlayerActivity and pass video URL
        val intent = Intent(this, VideoPlayer::class.java)
        intent.putExtra("videoUrl", video.videourl)
        intent.putExtra("title", video.title)
        intent.putExtra("channel", video.channelname)
        intent.putExtra("likes", video.likes)
        intent.putExtra("description", video.description)
        startActivity(intent)
    }


    suspend fun fetchVideosFromSupabase() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = supabaseClient.from("videos").select()
                val data = response.decodeList<videos>()
                videos.addAll(data)
                Log.e("priyanshu", data.toString())
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // Handle error
                Log.e("priyanshu", e.toString())
                Toast.makeText(
                    this@MainActivity,
                    "Error fetching videos: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

    }


}