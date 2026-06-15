import type { MuseumObjectDTO } from "~/dtos/MuseumObjectDTO";

const API_OBJECTS_URL = "/api/v1/objects";
const PAGE_SIZE = 4;

export type MuseumObjectPageResult = {
    items: MuseumObjectDTO[];
    hasNext: boolean;
}

export interface SearchFilters {
    type: string;
    category?: string;
    name?: string;
}

export async function getMuseumObject(id: number): Promise<MuseumObjectDTO[]> {
    const response = await fetch(`${API_OBJECTS_URL}/${id}`);

    if (!response.ok) {
        throw new Error("No se ha podido obtener el objeto.");
    }

    return await response.json();
}


export async function getMuseumObjects(
    page: number,
    filters: SearchFilters
): Promise<MuseumObjectPageResult> {

    const params = new URLSearchParams({
        page: page.toString(),
        size: PAGE_SIZE.toString(),
        type: filters.type
    });

    if (filters.category) {
        params.append("category", filters.category);
    }

    if (filters.name) {
        params.append("name", filters.name);
    }

    const response = await fetch(`${API_OBJECTS_URL}/?${params.toString()}`);

    if (!response.ok) {
        throw new Error("No se han podido obtener los objetos.");
    }

    const data = await response.json();

    if (Array.isArray(data?.content)) {
        return { items: data.content, hasNext: data.page.number < data.page.totalPages - 1 };
    }

    return { items: [], hasNext: false };
}


export async function getMuseumObjectsWithoutPage(): Promise<MuseumObjectDTO[]> {
    const response = await fetch(`${API_OBJECTS_URL}/list`);

    if (!response.ok) {
        throw new Error("No se han podido obtener los objetos.");
    }

    return await response.json();
}


export async function createMuseumObject(
    objectName: string, 
    groupName: string, 
    technicalData: string, 
    description: string, 
    type: string,          
    category: string       
): Promise<MuseumObjectDTO> {
    const response = await fetch(`${API_OBJECTS_URL}/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ 
            objectName, 
            groupName, 
            technicalData, 
            description, 
            type, 
            category,
            isSeen: false,
            notes: [],    
            image: null   
        })
    });

    if (!response.ok) {
        throw new Error("No se ha podido crear el objeto.");
    }

    return await response.json();
}

export async function replaceMuseumObject(
    id: number, 
    objectName: string, 
    groupName: string, 
    technicalData: string, 
    description: string, 
    type: string, 
    category: string
): Promise<MuseumObjectDTO> {
    const response = await fetch(`${API_OBJECTS_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ 
            id,
            objectName, 
            groupName, 
            technicalData, 
            description, 
            type,
            category,
            isSeen: false,
            notes: [],
            image: null
        })
    });

    if (!response.ok) {
        throw new Error("No se ha podido actualizar el objeto.");
    }

    return await response.json();
}


export async function deleteMuseumObject(id: number): Promise<void> {
    const response = await fetch(`${API_OBJECTS_URL}/${id}`, {
        method: "DELETE"
    });

    if (!response.ok) {
        throw new Error("No se ha podido eliminar el objeto.");
    }

}


export async function createObjectImage(id: number, imageFile: File): Promise<void> {
    const formData = new FormData();

    formData.append("imageFile", imageFile);

    const response = await fetch(`${API_OBJECTS_URL}/${id}/image`, {
        method: "POST",
        body: formData
    });

    if (!response.ok) {
        throw new Error("La imagen del objeto no ha podido ser creada.");
    }
}


export async function replaceObjectImage(id: number, imageFile: File): Promise<void> {
    const formData = new FormData();

    formData.append("imageFile", imageFile);

    const response = await fetch(`${API_OBJECTS_URL}/${id}/media`, {
        method: "PUT",
        body: formData
    });

    if (!response.ok) {
        throw new Error("La imagen del objeto no ha podido ser actualizada.");
    }
}


export async function deleteObjectImage(objectId: number, imageId: number): Promise<void> {
    const response = await fetch(`${API_OBJECTS_URL}/${objectId}/images/${imageId}`, {
        method: "DELETE"
    });

    if (!response.ok) {
        throw new Error("La imagen del objeto no ha podido ser eliminada.");
    }
}