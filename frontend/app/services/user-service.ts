import type { UserDTO } from "~/dtos/UserDTO";
import type { UserBasicDTO } from "~/dtos/UserBasicDTO";
import type { UserStatisticsDTO } from "~/dtos/UserStatisticsDTO";
import type { MuseumObjectBasicDTO } from "~/dtos/MuseumObjectBasicDTO";

const API_USERS_URL = "/api/v1/users";

export async function register(name: string, password: string, imageField: File | null): Promise<UserBasicDTO> {
  const formData = new FormData();
  formData.append("name", name);
  formData.append("password", password);

  if (imageField) {
    formData.append("imageField", imageField);
  }

  const response = await fetch(`${API_USERS_URL}/`, {
    method: "POST",
    body: formData,
  });

  if (!response.ok) {
    throw new Error("No se ha podido registrar al usuario.");
  }

  return await response.json();
}


export async function updateMyProfile(
  id: number, 
  name: string, 
  removeImage: boolean, 
  imageFile: File | null,
  password?: string 
): Promise<UserBasicDTO> {
  const formData = new FormData();
  formData.append("name", name);
  formData.append("removeImage", removeImage.toString());

  if (imageFile) {
    formData.append("imageField", imageFile);
  }
  
  if (password) {
    formData.append("password", password); 
  }

  const response = await fetch(`${API_USERS_URL}/me`, {
    method: "PUT",
    body: formData,
  });

  if (!response.ok) {
    throw new Error("No se ha podido actualizar el perfil del usuario.");
  }

  return await response.json();
}


export async function getMyStats(): Promise<UserStatisticsDTO> {
  const response = await fetch(`${API_USERS_URL}/me/statistics`);

  if (!response.ok) {
    throw new Error("No se han podido obtener las estadísticas del usuario.");
  }

  return await response.json();
}


export async function markObjectAsSeen(objectId: number): Promise<MuseumObjectBasicDTO> {
  const response = await fetch(`${API_USERS_URL}/me/seen/${objectId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ objectId }),
  });

  if (!response.ok) {
    throw new Error("No se ha podido marcar el objeto como visto.");
  }

  return await response.json();
}