import { CommonBadRequest, CommonNotFound } from "@/@types/http-error";
import { Task, TaskForm } from "@/@types/task";
import apiClient from "@/http-common";

export class TaskService {
  static async getAll(): Promise<Task[]> {
    // try {
    //   const response = await apiClient.get("/tasks/");
    //   return response.data;
    // } catch (e){
    //   if (axios.isAxiosError(e) && e.response && e.response.status === 400) {
    //
    //   }
    // }
    const response = await apiClient.get("/tasks/");
    return response.data.results;
  }

  static async get(id: number): Promise<Task> {
    const response = await apiClient.get(`/tasks/${id}`);
    return response.data;
  }

  static async create(form: TaskForm): Promise<Task> {
    const response = await apiClient.post("/tasks/", form);
    return response.data;
  }

  static async update(id: number, form: TaskForm): Promise<Task> {
    const response = await apiClient.put(`/tasks/${id}`, form);
    return response.data;
  }

  static async delete(id: number): Promise<void> {
    const response = await apiClient.delete(`/tasks/${id}`);
  }
}
