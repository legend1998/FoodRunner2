
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner2.R
import com.example.foodrunner2.model.Faqs

class FaqAdapter(val context: Context, var Faqlist: ArrayList<Faqs>) :
    RecyclerView.Adapter<FaqAdapter.FaqHolder>() {

    class FaqHolder(view: View) : RecyclerView.ViewHolder(view) {
        var question: TextView = view.findViewById(R.id.question)
        var answer: TextView = view.findViewById(R.id.answer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.faq_card, parent, false)
        return FaqHolder(view)

    }

    override fun getItemCount(): Int {
        return Faqlist.size
    }

    override fun onBindViewHolder(holder: FaqHolder, position: Int) {
        val faq = Faqlist[position]
        holder.question.text = faq.question
        holder.answer.text = faq.answer

    }
}