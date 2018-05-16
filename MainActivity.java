package seupacoteaqui.listviewdinamica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // atributos referentes ao XML
    private EditText ipt_item_name;
    private ListView lst_item;
    private Button btn_add_item;

    // instanciando ArrayList
    private ArrayList<String> items;

    // instanciando Adapter
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // buscando referencias
        ipt_item_name = findViewById(R.id.ipt_item_name);
        lst_item = findViewById(R.id.lst_item);
        btn_add_item = findViewById(R.id.btn_add_item);

        // definindo novo ArrayList
        items = new ArrayList<String>();

        // setando o ArrayList no Adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);

        // modo de visualizacao da ListView - ja definido no XML
        //lst_item.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // informa que a ListView tem menu de contexto
        registerForContextMenu(lst_item);

        // setando adapter no ListView
        lst_item.setAdapter(adapter);

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_name = ipt_item_name.getText().toString();

                if(isStringUsable(item_name)) {
                    // buscando tarefa do EditText
                    items.add(item_name);

                    // inverte a ordem das tarefas
                    // Collections.reverse(items);

                    // notificar a mudança para o adapter
                    adapter.notifyDataSetChanged();

                    // exibindo mensagem na tela
                    Toast.makeText(getBaseContext(), "Item adicionado!", Toast.LENGTH_SHORT).show();

                    // chamando metodo para limpar o EditText
                    clearField();
                } else {
                    Toast.makeText(getBaseContext(), "Campo vazio. Digite algo...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // metodo para verificar se o campo EditText contem texto (string)
    public Boolean isStringUsable(String string) {
        return string != null && !string.isEmpty();
        // retorna TRUE se a STRING for diferente de null E diferente de vazio
    }

    // metodo para limpar EditText
    private void clearField() {
        ipt_item_name.setText(null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        if(view.getId() == R.id.lst_item) {
            contextMenu.add(Menu.NONE, 0, Menu.NONE, R.string.br_menu_edit_item);
            contextMenu.add(Menu.NONE, 1, Menu.NONE, R.string.br_menu_delete_item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String item_name = adapter.getItem(info.position);

        switch(item.getItemId()) {
            case 0:
                // exibindo mensagem na tela
                Toast.makeText(getBaseContext(), "Opção EDITAR não implementada!", Toast.LENGTH_SHORT).show();

                // Seu codigo para editar o item aqui

                break;

            case 1:
                // exibindo mensagem na tela
                Toast.makeText(getBaseContext(), "Item \"" + item_name + "\" deletado!", Toast.LENGTH_SHORT).show();

                items.remove(info.position);
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

}