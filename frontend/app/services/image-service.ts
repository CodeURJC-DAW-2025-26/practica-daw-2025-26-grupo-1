import type {ImageDTO} from "~/dtos/ImageDTO";

const API_IMAGES_URL = "/api/v1/images";

export async function getImage(id: number): Promise<ImageDTO> {
    const response = await fetch(`${API_IMAGES_URL}/${id}`);

    if (!response.ok) {
        throw new Error("No se ha podido obtener la imagen.");
    }

    return await response.json();
}


export async function getImages(): Promise<[ImageDTO[]]> {
    const response = await fetch(`${API_IMAGES_URL}/`);

    if (!response.ok) {
        throw new Error("No se han podido obtener las imágenes.");
    }

    return await response.json();
}


export async function replaceImageFile(id: number, imageFile: File): Promise<void> {
    const formData = new FormData();

    formData.append("imageFile", imageFile);

    const response = await fetch(`${API_IMAGES_URL}/${id}`, {
        method: "PUT",
        body: formData
    });

    if (!response.ok) {
        throw new Error("No se ha podido actualizar la imagen.");
    }
}