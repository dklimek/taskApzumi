import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts-list',
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.css']
})
export class PostsListComponent implements OnInit {
  posts?: Post[];
  currentPost?: Post;
  currentIndex = -1;
  title = '';

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.retrievePosts();
  }

  retrievePosts(): void {
    this.postService.getAll()
      .subscribe(
        data => {
          this.posts = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  refreshList(): void {
    this.retrievePosts();
    this.currentPost = undefined;
    this.currentIndex = -1;
  }

  setActivePost(post: Post, index: number): void {
    this.currentPost = post;
    this.currentIndex = index;
  }


  searchTitle(): void {
    this.currentPost = undefined;
    this.currentIndex = -1;

    this.postService.findByTitle(this.title)
      .subscribe(
        data => {
          this.posts = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  updateDatabase(): void {
    this.currentPost = undefined;
    this.currentIndex = -1;
    
    this.postService.updateDatabase()
    .subscribe(
      response => {
        console.log(response);
        this.refreshList();
      },
      error => {
        console.log(error);
      });
  }

}
