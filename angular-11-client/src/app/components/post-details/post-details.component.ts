import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/models/post.model';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  currentPost: Post = {
    userId: 0,
    title: '',
    body: '' };
  message = '';

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.message = '';
    this.getPost(this.route.snapshot.params.id);
  }

  getPost(id: string): void {
    this.postService.get(id)
      .subscribe(
        data => {
          this.currentPost = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  updatePost(): void {
    this.message = '';

    this.postService.update(this.currentPost.id, this.currentPost)
      .subscribe(
        response => {
          console.log(response);
          this.message = response.message ? response.message : 'This post was updated successfully!';
        },
        error => {
          console.log(error);
        });
  }

  deletePost(): void {
    this.postService.delete(this.currentPost.id)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/post']);
        },
        error => {
          console.log(error);
        });
  }
}
