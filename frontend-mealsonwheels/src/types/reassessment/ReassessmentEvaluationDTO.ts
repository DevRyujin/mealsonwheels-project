// src/types/management/ReassessmentEvaluationDTO.ts

export interface ReassessmentEvaluationDTO {
  id: number;
  evaluationDate: string; // ISO date-time string
  evaluationNotes?: string;
  serviceAdjustments?: string;
  memberId: number;
  orderId?: number;
  rating?: number;
  comment?: string;
}
