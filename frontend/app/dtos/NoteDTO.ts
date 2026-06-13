import type { MuseumObjectDTO } from "./MuseumObjectDTO";
import type { UserDTO } from "./UserDTO";

export interface NoteDTO {
    id: number;
    text: string;
    user: UserDTO;
    museumObject: MuseumObjectDTO;
}