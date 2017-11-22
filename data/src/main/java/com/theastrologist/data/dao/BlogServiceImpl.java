package com.theastrologist.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	ArticleRepository articleRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Article> getArticles() {

		List<Article> articleList = new ArrayList<>();

		// lambda expression
		// findAll() retrieve an Iterator

		// method reference ...
		articleRepository.findAll().forEach(articleList::add);
		// ... it is equivalent to
		// articleRepository.findAll().forEach(article -&gt; articleList.add(article));

		return articleList;

	}
}