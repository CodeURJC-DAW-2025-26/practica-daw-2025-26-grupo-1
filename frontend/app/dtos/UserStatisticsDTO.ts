import type {CategoryStatsDTO} from "~/dtos/CategoryStatsDTO";

export interface UserStatisticsDTO {
    userName: string;
    globalTotalSeen: number;
    statsByCategory: CategoryStatsDTO[];
    globalTotals: Record <string, number>;
}