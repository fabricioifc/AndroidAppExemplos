package app.bizo.appclientevip.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import app.bizo.appclientevip.model.Usuario;
import app.bizo.appclientevip.views.R;
import app.bizo.appclientevip.views.UsuarioFormActivity;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private Context context;
    private List<Usuario> lista;
    private LayoutInflater mInflater;
    private final UsuarioAdapterListener listener;

    public UsuarioAdapter(Context context, List<Usuario> lista, UsuarioAdapterListener listener){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.lista = lista;
        this.listener = listener;
    }

    // stores and recycles views as they are scrolled off screen
    public class UsuarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView usuarioListaItemNome, usuarioListaItemEmail;
        private Usuario objeto;
        private WeakReference<UsuarioAdapterListener> listenerRef;

        UsuarioViewHolder(View itemView) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            usuarioListaItemNome = itemView.findViewById(R.id.usuarioListaItemNome);
            usuarioListaItemEmail = itemView.findViewById(R.id.usuarioListaItemEmail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listenerRef.get().onPositionClicked(getAdapterPosition(), objeto);
        }

        public void bind(Usuario usuario) {
            this.objeto = usuario;
            usuarioListaItemNome.setText(usuario.getNome());
            usuarioListaItemEmail.setText(usuario.getEmail());
        }

    }

    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.usuario_lista_linha, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
        Usuario usuario = lista.get(position);
        holder.bind(usuario);
    }

    public void addItem(Usuario model) {
        lista.add(model);
        notifyItemInserted(getItemCount());
    }

    public void removeItem(int posicao) {
        lista.remove(posicao);
        notifyItemRemoved(posicao);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void updateData(List<Usuario> lista) {
        this.lista.clear();
        this.lista.addAll(lista);
        notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View view = inflater.inflate(layoutResource, null);
//
//        TextView textViewName = view.findViewById(R.id.textViewName);
//        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
//        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
//        TextView textViewJoinDate = view.findViewById(R.id.textViewJoiningDate);
//
//        final Usuario usuario = lista.get(position);
//
//        textViewName.setText(usuario.getNome());
//        textViewDept.setText(usuario.getEmail());

//        view.findViewById(R.id.buttonDeleteEmployee).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteEmployee(usuario);
//            }
//        });
//
//        view.findViewById(R.id.buttonEditEmployee).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateEmployee(usuario);
//            }
//        });

//        return view;
//    }

//    private void updateEmployee(final Usuario employee) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.dialog_update_employee, null);
//        builder.setView(view);
//
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//        final EditText editTextName = view.findViewById(R.id.editTextName);
//        final EditText editTextSalary = view.findViewById(R.id.editTextSalary);
//        final Spinner spinner = view.findViewById(R.id.spinnerDepartment);
//
//        editTextName.setText(employee.getName());
//        editTextSalary.setText(String.valueOf(employee.getSalary()));
//
//
//        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String name = editTextName.getText().toString().trim();
//                String salary = editTextSalary.getText().toString().trim();
//                String dept = spinner.getSelectedItem().toString().trim();
//
//                if (name.isEmpty()) {
//                    editTextName.setError("Name can't be empty");
//                    editTextName.requestFocus();
//                    return;
//                }
//
//                if (salary.isEmpty()) {
//                    editTextSalary.setError("Salary can't be empty");
//                    editTextSalary.requestFocus();
//                    return;
//                }
//
//
//                //calling the update method from database manager instance
//                if (mDatabase.updateEmployee(employee.getId(), name, dept, Double.valueOf(salary))) {
//                    Toast.makeText(mCtx, "Employee Updated", Toast.LENGTH_SHORT).show();
//                    loadEmployeesFromDatabaseAgain();
//                }
//                alertDialog.dismiss();
//            }
//        });
//    }
//
//    private void deleteEmployee(final Employee employee) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
//        builder.setTitle("Are you sure?");
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                //calling the delete method from the database manager instance
//                if (mDatabase.deleteEmployee(employee.getId()))
//                    loadEmployeesFromDatabaseAgain();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

//    private void loadEmployeesFromDatabaseAgain() {
//        //calling the read method from database instance
//        Cursor cursor = mDatabase.getAllEmployees();
//
//        employeeList.clear();
//        if (cursor.moveToFirst()) {
//            do {
//                employeeList.add(new Employee(
//                        cursor.getInt(0),
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getString(3),
//                        cursor.getDouble(4)
//                ));
//            } while (cursor.moveToNext());
//        }
//        notifyDataSetChanged();
//    }
}
