import type { UserDTO } from "./UserDTO";

export interface NoteBasicDTO {
    id: number;
    text: string;
    user: UserDTO;
}