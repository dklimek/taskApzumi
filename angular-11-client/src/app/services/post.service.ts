import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../models/post.model';

const baseUrl = 'http://localhost:8080/api/post';


@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Post[]> {
    return this.http.get<Post[]>(`${baseUrl}/rodo`);
  }

  get(id: any): Observable<Post> {
    return this.http.get(`${baseUrl}/rodo/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/delete/${id}`);
  }

  findByTitle(title: any): Observable<Post[]> {
    return this.http.get<Post[]>(`${baseUrl}/rodo/ContainingTittle/${title}`);
  }

  updateDatabase(): Observable<Post> {
    return this.http.get(`${baseUrl}/downloaddatatodatabase`);
  }
}
