import type { UserBasicDTO } from "./UserBasicDTO";

export interface NoteBasicDTO {
    id: number;
    text: string;
    user: UserBasicDTO;
}