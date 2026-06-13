import type { NoteDTO } from "./NoteDTO";

export interface MuseumObjectDTO {
    id: number;
    objectName: string;
    groupName: string;
    technicalData: string;
    description: string;
    type: string;
    category: string;
    notes: NoteDTO[];
    isSeen: boolean;
}

