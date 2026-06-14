import type { NoteDTO } from "./NoteDTO";
import type { ImageDTO } from "./ImageDTO";

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
    image: ImageDTO;
}

