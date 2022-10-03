from main import do_visit

import tkinter as tk



def run_code():
    input = txt_edit.get("1.0",tk.END)
    text, errors = do_visit(input)
    if len(errors) > 0:
        for e in errors:
            insert_result(e + '\n') 
    insert_result(text)


def insert_result(text):
    txt_result.config(state=tk.NORMAL)
    txt_result.insert('end', text)
    txt_result.config(state=tk.DISABLED)


def clear_result():
    txt_result.config(state=tk.NORMAL)
    txt_result.delete('1.0', tk.END)
    txt_result.config(state=tk.DISABLED)

# window configs
window = tk.Tk()
window.title("YAPL Code Analyzer")
window.rowconfigure(0, minsize=800, weight=1)
window.columnconfigure(1, minsize=800, weight=1)

# textboxes
txt_edit = tk.Text(window)
txt_result = tk.Text(window)

txt_edit.configure(font=("Helvetica",16))
txt_result.config(state=tk.DISABLED)

# button layout
fr_buttons = tk.Frame(window, relief=tk.RAISED, bd=2)
btn_open = tk.Button(fr_buttons, text="RUN", command=run_code)
btn_clr  = tk.Button(fr_buttons, text="CLEAR", command=clear_result)

btn_open.grid(row=0, column=0, sticky="ew", padx=5, pady=5)
btn_clr.grid(row=1, column=0, sticky="ew", padx=5, pady=5)

# components positions
fr_buttons.grid(row=0, column=0, sticky="ns")
txt_edit.grid(row=0, column=1, sticky="nsew")
txt_result.grid(row=0, column=2, sticky="nsew")

# enter loop for GUI
window.mainloop()