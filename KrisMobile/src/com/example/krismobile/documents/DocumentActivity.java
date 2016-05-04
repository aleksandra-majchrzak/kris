package com.example.krismobile.documents;

import java.util.Currency;
import java.util.Map;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.couchbase.lite.Document;
import com.example.krismobile.R;
import com.example.krismobile.contractors.Contractor;
import com.example.krismobile.database.managers.DocumentsManager;
import com.example.krismobile.documents.adapters.DocumentSectionsPagerAdapter;

public class DocumentActivity extends Activity implements ActionBar.TabListener{

	DocumentSectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	
	private KrisDocument document;
	private Contractor contractor;
	private boolean isNewDocument = true;
	private boolean editingDocument = false;
	private int activePage = 0;
	
	private Tab itemsTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Bundle args = getIntent().getExtras();
		String documentId = args.getString("documentId");
		contractor = args.getParcelable("contractor");
		int docTypeId = args.getInt("docTypeId");
		
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	
		
		if(savedInstanceState!= null && ! savedInstanceState.isEmpty()){
			document = (KrisDocument) savedInstanceState.getParcelable("document");
			activePage = savedInstanceState.getInt("activePage");
			
			if(!documentId.equals("")){
				
				this.isNewDocument = false;
				getActionBar().setTitle(document.getNumber());
				
			} 
			else {
				
				this.isNewDocument = true;			
				getActionBar().setTitle(getResources().getString(R.string.action_add_document));
				
			}
			
			editingDocument =savedInstanceState.getBoolean("isBeingEdited");
		} 
		else{
			if(!documentId.equals("")){
				this.isNewDocument = false;
				
				Document documentDoc = DocumentsManager.getInstance().getKrisDocument(documentId);
				Map<?,?> props = documentDoc.getProperties();
				
				document = new KrisDocument(documentDoc);
				
				getActionBar().setTitle((String)props.get("Number"));
			}
			else{
				this.isNewDocument = true;
				editingDocument = true;
				
				document = new KrisDocument(docTypeId);
				document.setContractor(contractor);
				
				getActionBar().setTitle(getResources().getString(R.string.action_add_document));
			}
		}
			
		setContentView(R.layout.activity_document);

		mSectionsPagerAdapter = new DocumentSectionsPagerAdapter(getFragmentManager(), this, editingDocument);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	getActionBar().setSelectedNavigationItem(position);
            }
        });
		
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			
			getActionBar().addTab(
					getActionBar().newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
		
		mViewPager.setCurrentItem(activePage);
		
		super.onCreate(savedInstanceState);
	}	
	
	
	@Override
	protected void onSaveInstanceState(Bundle stateBundle){
		
		activePage = mViewPager.getCurrentItem();		
		
		stateBundle.putParcelable("document", this.document);		
		stateBundle.putInt("activePage", activePage);
		stateBundle.putBoolean("isBeingEdited", editingDocument);
	}
/*	
	public Contractor reloadDocument(){
		Document documentDoc = ContractorsManager.getInstance().getContractor(contractor.getId());
		Map<?,?> props = contractorDoc.getProperties();
		
		contractor.setCode((String)props.get("Code"));
		contractor.setTypeId((Integer)props.get("TypeId"));
		contractor.setAddress((String)props.get("Address"));
		contractor.setDescription((String)props.get("Description"));
		contractor.setNIP((String)props.get("NIP"));
		
		getActionBar().setTitle((String)props.get("Code"));
		
		return contractor;
	}
*/	
	public void setIsBeingEdited(boolean beingEdited){
		editingDocument = beingEdited;
		
/*		mSectionsPagerAdapter.setDocumentEditing(editingDocument);
		
		
		if(beingEdited){
			getActionBar().addTab(
					getActionBar().newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(0))
                            .setTabListener(this), 0, true);			
			
		}else{
			getActionBar().removeTabAt(0);
		}
		mSectionsPagerAdapter.notifyDataSetChanged();
*/			
	}
	
	public boolean getIsBeingEdited(){
		return editingDocument;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if(isNewDocument || editingDocument)
			getMenuInflater().inflate(R.menu.document_modify, menu);
		else
			getMenuInflater().inflate(R.menu.document, menu);
			
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public KrisDocument getDocument(){
		return this.document;
	}
	
	public Contractor getContractor(){
		return this.contractor;
	}
	

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}

}
