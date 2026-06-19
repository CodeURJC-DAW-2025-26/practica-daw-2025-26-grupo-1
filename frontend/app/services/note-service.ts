import type { NoteDTO } from "~/dtos/NoteDTO";
import type { NoteBasicDTO } from "~/dtos/NoteBasicDTO";
import type { MuseumObjectBasicDTO } from "~/dtos/MuseumObjectBasicDTO";

const API_NOTES_URL = "/api/v1/notes";
const PAGE_SIZE = 10;

export type NotePageResult = {
    items: NoteBasicDTO[];
    hasNext: boolean;
}


export async function getNote(noteId: number): Promise<NoteBasicDTO> {
    const response = await fetch(`${API_NOTES_URL}/${noteId}`);

    if (!response.ok) {
        throw new Error("No se ha podido obtener la nota.");
    }

    return await response.json();
}


export async function getNotesByObject(objectId: number): Promise<NoteBasicDTO[]> {
    const response = await fetch(`${API_NOTES_URL}/object/${objectId}`);

    if (!response.ok) {
        throw new Error("No se han podido obtener las notas del objeto seleccionado.");
    }

    return await response.json();
}


export async function getAllNotes(page: number): Promise<NotePageResult> {
    const response = await fetch(`${API_NOTES_URL}/?page=${page}&size=${PAGE_SIZE}`);

    if (!response.ok) {
        throw new Error("No se han podido obtener las notas.");
    }

    const data = await response.json();

    if (Array.isArray(data?.content)) {
        return { items: data.content, hasNext: !data.last };
    }

    return { items: [], hasNext: false };
}

export async function getNotesByUser(page: number): Promise<{ items: NoteDTO[]; hasNext: boolean }> {
    const response = await fetch(`${API_NOTES_URL}/user?page=${page}&size=${PAGE_SIZE}`);

    if (!response.ok) {
        throw new Error("No se han podido obtener las notas.");
    }

    const data = await response.json();

    if (data && Array.isArray(data.content)) {
        return { items: data.content, hasNext: !data.last };
    }

    return { items: [], hasNext: false };
}


export async function createNote(objectId: number, text: string): Promise<NoteBasicDTO> {
    const response = await fetch(`${API_NOTES_URL}/object/${objectId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ text }),
    });

    if (!response.ok) {
        throw new Error("No se ha podido crear la nota.");
    }

    return await response.json();
}

export async function deleteNote(id: number): Promise<void> {
    const response = await fetch(`${API_NOTES_URL}/${id}`, {
        method: "DELETE",
    });

    if (!response.ok) {
        throw new Error("No se ha podido eliminar la nota.");
    }
}