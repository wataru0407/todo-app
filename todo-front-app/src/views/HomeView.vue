<template>
  <div>
    <!-- task追加用のテキストボックス -->
    <v-text-field
      v-model="newTaskTitle"
      class="mt-3 ml-3"
      label="Add Task"
      @keyup.enter="addTask"
      clearable
      hide-details
      counter
      maxlength="20"
      prepend-icon="mdi-plus"
    ></v-text-field>
    <v-list>
      <!-- divでtasksをループで出力する。templateは使えない。 -->
      <div v-for="task in tasks" :key="task.id">
        <!-- クリックしたら、背景色を変更し、tasksを更新するイベントを追加 -->
        <v-list-item @click="doneTask(task.id)" :class="{ 'blue lighten-5': task.done }">
          <template>
            <!-- タイトル、チェックボックスをtasksと連動させる -->
            <v-list-item-action>
              <v-checkbox :input-value="task.done"></v-checkbox>
            </v-list-item-action>

            <v-list-item-content>
              <!-- タスクが完了したら取り消し線にする。 -->
              <v-list-item-title :class="{ 'text-decoration-line-through': task.done }">{{
                task.title
              }}</v-list-item-title>
            </v-list-item-content>

            <!-- deleteアイコンに変更し、tasksを削除するイベント追加 -->
            <v-list-item-icon>
              <v-icon color="primary" @click="deleteTask(task.id)"> mdi-delete </v-icon>
            </v-list-item-icon>
          </template>
        </v-list-item>
        <v-divider></v-divider>
      </div>
    </v-list>
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "Home",
  // tasksを追加
  data: () => ({
    newTaskTitle: "",
    tasks: [
      {
        id: 1,
        title: "起きる",
        done: false,
      },
      {
        id: 2,
        title: "着替える",
        done: false,
      },
    ],
  }),
  methods: {
    // taskを検索し、フラグを更新する。
    doneTask(id: number) {
      let task = this.tasks.find((t) => t.id === id);
      if (task !== undefined) {
        task.done = !task.done;
      }
    },
    // taskを削除する。
    deleteTask(id: number) {
      this.tasks.forEach((task, index) => {
        if (task.id == id) this.tasks.splice(index, 1);
      });
      // 以下の書き方もできるが、部品化すると更新できなくなるので却下
      // this.tasks = this.tasks.filter((t) => t.id !== id);
    },
    // taskを追加する。
    addTask() {
      if (this.newTaskTitle.length > 0) {
        let newTask = {
          id: Date.now(),
          title: this.newTaskTitle,
          done: false,
        };
        this.tasks.push(newTask);
        this.newTaskTitle = "";
      }
    },
  },
});
</script>
