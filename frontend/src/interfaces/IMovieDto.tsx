export interface IMovieDto {
  id: string;
  title: string;
  plot: string;
  rating: number;
  releaseYear: string;
  runtime: number;
  posterUrl: string;
  genres: string[];
  directors: string[];
  actors: string[];
}
