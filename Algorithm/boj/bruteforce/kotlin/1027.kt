import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
 
val br = BufferedReader(InputStreamReader(System.`in`))
val bw = BufferedWriter(OutputStreamWriter(System.out))
fun main()=with(br){
    val n = readLine()!!.toInt()
    val seeCount= IntArray(n,{0})
    var inclination = DoubleArray(n,{0.0})
    val graph = readLine()!!.split(" ").map{it.toDouble()}
    for(i in 0 until graph.size){
        var count = 0
        var count2 = 0
        var y  = graph[i]
        for(j in 0 until i){
            inclination[j]=(y-graph[j])/(i-j)
        }
        for(j in graph.size-1 downTo i+1)
        {
            inclination[j]=(graph[j]-y)/(j-i)
        }
        var tmp = 0.0
        for(j in 0 until i){
            tmp = inclination[j]
            var key = true
            for(k in j+1 until i){
                if(tmp>=inclination[k]){
                    key = false
                    break
                }
            }
            if(key){
                count++
            }
        }
        for(j in graph.size-1 downTo i+1) {
            tmp = inclination[j]
            var key = true
            for(k in j-1 downTo i+1){
               // println(inclination[k])
                if(tmp<=inclination[k]){
                    key = false
                    break
                }
            }
            if(key){
                count2++
            }
        }
 
        seeCount[i]=count+count2
    }
 
    var ans = seeCount.maxByOrNull { it }
    println(ans)
    bw.flush()
    bw.close()
}
 
