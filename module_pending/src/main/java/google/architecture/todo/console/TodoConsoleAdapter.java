package google.architecture.todo.console;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import google.architecture.coremodel.datamodel.http.entities.TodoData;
import google.architecture.todo.R;
import google.architecture.todo.databinding.TodoConsoleItemBinding;
import google.architecture.todo.utils.Constants;

/**
 * @description:
 * @author: HuaiAngg
 * @create: 2019-05-04 20:36
 */
public class TodoConsoleAdapter extends RecyclerView.Adapter<TodoConsoleAdapter.TodoConcoleViewHolder> {

    List<TodoData.ConsoleResultsBean> consoleList;
    TodoConsoleClickCallback consoleItemClickCallback;

    public TodoConsoleAdapter(TodoConsoleClickCallback consoleItemClickCallback) {
        this.consoleItemClickCallback = consoleItemClickCallback;
    }

    public void setConsoleList(final List<TodoData.ConsoleResultsBean> list) {
        if (consoleList == null) {
            consoleList = list;
            notifyItemRangeInserted(0, consoleList.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return consoleList.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    TodoData.ConsoleResultsBean oldData = consoleList.get(oldItemPosition);
                    TodoData.ConsoleResultsBean newData = list.get(newItemPosition);
                    return oldData.getName() == newData.getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    TodoData.ConsoleResultsBean oldData = consoleList.get(oldItemPosition);
                    TodoData.ConsoleResultsBean newData = list.get(newItemPosition);
                    return oldData.getName() == newData.getName()
                            && oldData.getIcon() == newData.getIcon();
                }
            });
            consoleList = list;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public TodoConcoleViewHolder onCreateViewHolder(ViewGroup parent, int positon) {
        TodoConsoleItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.todo_console_item,
                        parent, false);
        binding.setConsoleCallback(consoleItemClickCallback);
        return new TodoConcoleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TodoConcoleViewHolder holder, int position) {
        holder.binding.setConsoleItem(consoleList.get(position));
        holder.binding.todoConsoleListIcon.setImageResource(Constants.TODO_CONSOLE_LIST_ICON[position]);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return consoleList == null ? null : consoleList.size();
    }

    static class TodoConcoleViewHolder extends RecyclerView.ViewHolder {
        TodoConsoleItemBinding binding;

        public TodoConcoleViewHolder(TodoConsoleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}