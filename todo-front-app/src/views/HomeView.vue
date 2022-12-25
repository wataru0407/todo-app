<template>
  <v-data-table
    :headers="headers"
    :items="tasks"
    sort-by="calories"
    class="elevation-1"
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>My CRUD</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template #activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              New Item
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-text-field
                  v-model="editedItem.title"
                  label="Title"
                ></v-text-field>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
              <v-btn color="blue darken-1" text @click="save"> Save </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Are you sure you want to delete this item?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template #[`item.actions`]="{ item }">
      <v-icon small class="mr-2" @click="editItem(item.id)">
        mdi-pencil
      </v-icon>
      <v-icon small @click="deleteItem(item.id)"> mdi-delete </v-icon>
    </template>
  </v-data-table>
</template>

<script lang="ts">
import axios from "axios";
import Vue from "vue";
import { Task, TaskForm } from "@/@types/task";

export default Vue.extend({
  data() {
    return {
      dialog: false,
      dialogDelete: false,
      headers: [
        { text: "ID", value: "id", align: "start", sortable: false },
        { text: "Title", value: "title", sortable: false },
        { text: "Actions", value: "actions", align: "end", sortable: false },
      ],
      tasks: [] as Task[],
      // editedIndex: -1,
      editedItem: {
        id: -1,
        title: "",
      } as Task,
      defaultItem: {
        id: -1,
        title: "",
      } as Task,
      deletedItemId: -1,
    };
  },

  computed: {
    formTitle(): string {
      return this.editedItem.id === -1 ? "New Item" : "Edit Item";
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize(): void {
      axios
        .get("/tasks/")
        .then((response) => {
          this.tasks = response.data.results;
          console.log(this.tasks);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    createItem(task: Task): void {
      const taskForm: TaskForm = {
        title: task.title,
      };
      axios
        .post("/tasks/", taskForm)
        .then((response) => {
          console.log(response.data);
          this.initialize();
        })
        .catch((error) => {
          console.log(error);
        });
    },

    editItem(id: number): void {
      // this.editedIndex = this.tasks.indexOf(item);
      // this.editedItem = Object.assign({}, item);
      // this.dialog = true;
      axios
        .get("/tasks/" + id)
        .then((response) => {
          this.editedItem = response.data;
          console.log(this.editedItem);
          this.dialog = true;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    updateItem(task: Task): void {
      const taskForm: TaskForm = {
        title: task.title,
      };
      axios
        .put("/tasks/" + task.id, taskForm)
        .then((response) => {
          console.log(response.data);
          this.initialize();
        })
        .catch((error) => {
          console.log(error);
        });
    },

    deleteItem(id: number): void {
      //this.editedIndex = this.tasks.indexOf(item);
      //this.editedItem = Object.assign({}, item);
      this.deletedItemId = id;
      this.dialogDelete = true;
    },

    deleteItemConfirm(): void {
      //this.tasks.splice(this.editedIndex, 1);
      axios
        .delete("/tasks/" + this.deletedItemId)
        .then(() => {
          this.initialize();
        })
        .catch((error) => {
          console.log(error);
        });
      this.closeDelete();
    },

    close(): void {
      this.dialog = false;
      // this.deleteDialog = false;
      this.$nextTick(() => {
        // this.editedItem = Object.assign({}, this.defaultItem);
        // this.editedIndex = -1;
        this.editedItem = Object.assign({}, this.defaultItem);
      });
    },

    closeDelete(): void {
      this.dialogDelete = false;
      this.$nextTick(() => {
        // this.editedItem = Object.assign({}, this.defaultItem);
        // this.editedIndex = -1;
        this.editedItem = Object.assign({}, this.defaultItem);
        this.deletedItemId = -1;
      });
    },

    save(): void {
      // if (this.editedIndex > -1) {
      //   Object.assign(this.tasks[this.editedIndex], this.editedItem);
      // } else {
      //   this.tasks.push(this.editedItem);
      // }
      // this.close();
      if (this.editedItem.id === -1) {
        this.createItem(this.editedItem);
      } else {
        this.updateItem(this.editedItem);
      }
      this.close();
    },
  },
});
</script>
