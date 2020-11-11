package ru.kpfu.stud.bottomnavigation

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.add_item.view.*
import kotlinx.android.synthetic.main.fragment_2.*

class BlankFragment2 : Fragment() {

    var adapter : PlanetAdapter? = null
    var dialogBuilder: AlertDialog.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlanetAdapter(PlanetsObject.getElements().toList()) {
            PlanetsObject.planetsObject.removeAt(it)
            adapter?.updateData(PlanetsObject.getElements().toList())
        }
        recycler_planet.adapter = adapter
        recycler_planet.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val add = LayoutInflater.from(context).inflate(R.layout.add_item, null)
        dialogBuilder = AlertDialog.Builder(context)
                .setTitle("Добавить карточку:")
                .setView(add)
                .setNegativeButton("Отмена") { _: DialogInterface, _: Int -> }
                .setPositiveButton("Сохранить") { _: DialogInterface?, _: Int ->
                    val itemAdd = Planet(add.newName.text.toString(),
                                        add.descr.text.toString(),
                                        R.drawable.new_planet)
                    val pos = add.posit.text.toString()
                    if (pos.isEmpty()) PlanetsObject.planetsObject.add(itemAdd)
                    else PlanetsObject.addElement(itemAdd, Integer.parseInt(pos))
                    adapter?.updateData(PlanetsObject.getElements().toList())
                }
        val dialog = dialogBuilder?.create()
        float_button.setOnClickListener {
            dialog?.show()
        }
    }

}