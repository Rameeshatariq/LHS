package com.akdndhrc.hayat.lhs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.akdndhrc.hayat.lhs.Adapter.Validation_Extandable_List;
import com.akdndhrc.hayat.lhs.Checklist.LHS_Checklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Validation extends AppCompatActivity {
    String activity_id,activity_month;
    Validation_Extandable_List listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listCount;
    HashMap<String, List<String>> listDataChild;
    String basicinfo[][], childhealth[][], maternalhealth[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);


        // setting list adapter


        // preparing list data
        activity_id=getIntent().getExtras().getString("activity_id");
        activity_month=getIntent().getExtras().getString("activity_month");

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        prepareListData();

        listAdapter = new Validation_Extandable_List(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {


        try{
            Lister ls= new Lister(Validation.this);
            ls.createAndOpenDB();

            String res= "Select household_registered_lhw, tap, spring, handpump, well, other," +
                    " age_1223_count, age_1223_fully_imunized, age_lt3_count, age_lt3_gm, age_lt3_malnurished, new_borns_1week," +
                    " low_birth_weight, breast_fed, immunized,new_preg,total_preg,total_vistis,iron_sup,abortions,delivey_4p,delivery_pnc," +
                    " delivery_immunized,eligible,provided, followup,modern,condom_users,pill_users,injectible_users,iucd_users,surgical_users," +
                    " other_users,traditional_users,referred,supplied_condoms,supplied_pills,supplied_injectibles,diarrhea_a5,diarrhea_u5," +
                    " ari_a5,ari_u5,fever_a5,fever_u5,resp_a5,resp_u5,anaemia_a5,anaemia_u5,scabies_a5,scabies_u5,eye_infections_a5,eye_infections_u5," +
                    " rtis_a5,rtis_u5,worm_a5,worm_u5,malaria_a5,malaria_u5,referral_a5,referral_u5,tb_suspect_a5,tb_suspect_u5,tb_diagnosed_a5," +
                    " tb_diagnosed_u5,tb_followed_a5,tb_followed_u5,live,still,deaths_all,noenatal,infant,children,maternal,tab_paracetamol," +
                    " syp_paracetamol,tab_choloroquin,syp_choloroquin,tab_mebendazole,syp_pipearzine,ors,eye_ontiment,syp_contrimexazole,iron_tab," +
                    " antiseptic_lotion,benzyle_benzoate_lotion,sticking_plaster,b_complex_syp,cotton_bandages,cotton_wool,condoms,oral_pills," +
                    " contraceptive_inj,med_others,lhw_kit_bag,weighing_machine,thermometer,torch_with_cell,scissors,syringe_cutter,mis_others," +
                    " lhs,dco,adc,fpo,ppiu, max(added_on) from MONTHLY_REPORT where month = '"+activity_month+"'";
            //basicinfo= ls.executeReader("Select household_registered_lhw, tap, spring, handpump, well, other from MONTHLY_REPORT ");
            childhealth= ls.executeReader(res);

            Log.d("000999", "prepareListData: "+res);

            for (int i = 0; i<childhealth.length; i++){
                Log.d("000999", "prepareListData: "+childhealth[i][0]);
            }
            Log.d("000999", "prepareListData: "+childhealth[0][0]);


        }
        catch (Exception e){
            Log.d("000999", "prepareListData: "+e);
        }


        // Adding header data
        listDataHeader.add("Basic Information");
        listDataHeader.add("Social Contacts");
        listDataHeader.add("Child Health");
        listDataHeader.add("Mother Health");
        listDataHeader.add("Family Planning");
        listDataHeader.add("Common Ailments of Patients above 5 years");
        listDataHeader.add("Common Ailments of Patients under 5 years");
        listDataHeader.add("Births/Deaths");
        listDataHeader.add("Logistic (LHW Kit)");
        listDataHeader.add("Miscellanous");
        listDataHeader.add("Supervision");

        try {
            // Adding child data
            List<String> basicInformation = new ArrayList<String>();
            basicInformation.add("No of Formulated Health Commettees" + "@" + "0");
            basicInformation.add("No of Formulated Women Support Groups" + "@" + "1");
            basicInformation.add("Household Registered by LHWs" + "@" + childhealth[0][0]);
            basicInformation.add("Household with Source of Drinking Water, Tap" + "@" + childhealth[0][1]);
            basicInformation.add("Household with Source of Drinking Water, Spring" + "@" + childhealth[0][2]);
            basicInformation.add("Household with Source of Drinking Water, Handpump" + "@" + childhealth[0][3]);
            basicInformation.add("Household with Source of Drinking Water, other" + "@" + childhealth[0][4]);
            basicInformation.add("Household with Source of Drinking Water, other" + "@" + childhealth[0][5]);
            basicInformation.add("Household having Flush System with Sewerage/Septic Tank" + "@" + "3");

            List<String> socialContacts = new ArrayList<String>();
            socialContacts.add("No Of Health Committee Meetings" + "@" + "0");
            socialContacts.add("No Of Women Support Group Meetings" + "@" + "0");
            socialContacts.add("No of Health Education Sessions Held in School" + "@" + "0");

            List<String> childHealth = new ArrayList<String>();
            childHealth.add("No of New Borns Weighted (With in One Week After Birth)" + "@" + childhealth[0][11]);
            childHealth.add("No of Low Birth Weight Babies" + "@" + childhealth[0][12]);
            childHealth.add("No of New Born Started Breast Feeding within 24 Hours" + "@" + childhealth[0][13]);
            childHealth.add("No of Infants whose Immunization has started this Month" + "@" + childhealth[0][14]);
            childHealth.add("No of 12-23 Months old children" + "@" + childhealth[0][6]);
            childHealth.add("No of 12-23 Months old children Fully immunized" + "@" + childhealth[0][7]);
            childHealth.add("No of <3 Years children" + "@" + childhealth[0][8]);
            childHealth.add("No of <3 Years Children whose GM done & Recorded" + "@" + childhealth[0][9]);
            childHealth.add("No Of <3 Years Children malnourished" + "@" + childhealth[0][10]);


            List<String> maternal_health = new ArrayList<String>();
            maternal_health.add("No. of newly registered pregnant Women" + "@" + childhealth[0][15]);
            maternal_health.add("Total Pregnant Women (New+ Follow up)" + "@" + childhealth[0][16]);
            maternal_health.add("Total Pregnant Women visited (New + Followup)" + "@" + childhealth[0][17]);
            maternal_health.add("No. of Pregnant women supplied Iron tablets" + "@" + childhealth[0][18]);
            maternal_health.add("No. of Abortions (pregnancy less Than 7 Months)" + "@" + childhealth[0][19]);
            maternal_health.add("No of Women Delivered having 4 or >4 ANC Visits by SBAs (Dr, Nurse, LHV, Midwife/CMW)" + "@" + childhealth[0][20]);
            maternal_health.add("No of Women Delivered With TT completed Before Delivery" + "@" + childhealth[0][21]);
            maternal_health.add("No Of Deliveries by Skilled Birth Attendants (Dr, Nurse, LHV, Midwife/CMW)" + "@" + childhealth[0][22]);
            maternal_health.add("No. of Post-natal Cases Visited within 24 Hours" + "@" + "0");

            List<String> family_planning = new ArrayList<String>();
            family_planning.add("No of Eligible Couples (Age of Wife between 15-49 Years)" + "@" + childhealth[0][23]);
            family_planning.add("No of New Client of Family Planning (Modern + Traditional)" + "@" + childhealth[0][24]);
            family_planning.add("No of Follow-up Cases for Family Planning (Modern + Traditional)" + "@" + childhealth[0][25]);
            family_planning.add("Total No of Modern Contraceptive Method Users" + "@" + childhealth[0][26]);
            family_planning.add("No of Condom Users" + "@" + childhealth[0][27]);
            family_planning.add("No of Oral Pills Users" + "@" + childhealth[0][28]);
            family_planning.add("No of Injectable Contraceptive Users" + "@" + childhealth[0][29]);
            family_planning.add("No of IUCD Client" + "@" + childhealth[0][30]);
            family_planning.add("No of Surgical Clients" + "@" + childhealth[0][31]);
            family_planning.add("No of Other Modern Contraceptive Method Users" + "@" + childhealth[0][32]);
            family_planning.add("Total No of Traditional Method Users" + "@" + childhealth[0][33]);
            family_planning.add("No of Family Planning Clients referred" + "@" + childhealth[0][34]);
            family_planning.add("No of Clients Supplied condoms" + "@" + childhealth[0][35]);
            family_planning.add("No of Clients Supplied Oral pills" + "@" + childhealth[0][36]);
            family_planning.add("No of Clients administered Injectable Contraceptives" + "@" + childhealth[0][37]);

            List<String> common_aliments_a5 = new ArrayList<String>();
            common_aliments_a5.add("No of Cases of Diarrhoea" + "@" + childhealth[0][38]);
            common_aliments_a5.add("No of Cases of ARI" + "@" + childhealth[0][40]);
            common_aliments_a5.add("No of Cases of Fever" + "@" + childhealth[0][42]);
            common_aliments_a5.add("No of Cases of Injuries/burns" + "@" + childhealth[0][44]);
            common_aliments_a5.add("No of Cases of Anaemia" + "@" + childhealth[0][46]);
            common_aliments_a5.add("No of Cases of Scabies" + "@" + childhealth[0][48]);
            common_aliments_a5.add("No of Cases of Eye Infections" + "@" + childhealth[0][50]);
            common_aliments_a5.add("No of Female Cases of reproductive tract infections" + "@" + childhealth[0][52]);
            common_aliments_a5.add("No of Cases of Worm Infestation" + "@" + childhealth[0][54]);
            common_aliments_a5.add("No of Cases of Malaria" + "@" + childhealth[0][56]);
            common_aliments_a5.add("No of Referral Cases" + "@" + childhealth[0][58]);
            common_aliments_a5.add("No of of Suspected Cases of TB Referred" + "@" + childhealth[0][60]);
            common_aliments_a5.add("No of Diagnosed Cases of TB" + "@" + childhealth[0][62]);
            common_aliments_a5.add("No of TB Patients followed by LHW (as T/M Support)" + "@" + childhealth[0][64]);

            List<String> common_aliments_u5 = new ArrayList<String>();
            common_aliments_u5.add("No of Cases of Diarrhoea" + "@" + childhealth[0][39]);
            common_aliments_u5.add("No of Cases of ARI" + "@" + childhealth[0][41]);
            common_aliments_u5.add("No of Cases of Fever" + "@" + childhealth[0][43]);
            common_aliments_u5.add("No of Cases of Injuries/burns" + "@" + childhealth[0][45]);
            common_aliments_u5.add("No of Cases of Anaemia" + "@" + childhealth[0][47]);
            common_aliments_u5.add("No of Cases of Scabies" + "@" + childhealth[0][49]);
            common_aliments_u5.add("No of Cases of Eye Infections" + "@" + childhealth[0][51]);
            common_aliments_u5.add("No of Female Cases of reproductive tract infections" + "@" + childhealth[0][53]);
            common_aliments_u5.add("No of Cases of Worm Infestation" + "@" + childhealth[0][55]);
            common_aliments_u5.add("No of Cases of Malaria" + "@" + childhealth[0][57]);
            common_aliments_u5.add("No of Referral Cases" + "@" + childhealth[0][59]);
            common_aliments_u5.add("No of of Suspected Cases of TB Referred" + "@" + childhealth[0][61]);
            common_aliments_u5.add("No of Diagnosed Cases of TB" + "@" + childhealth[0][63]);
            common_aliments_u5.add("No of TB Patients followed by LHW (as T/M Support)" + "@" + childhealth[0][65]);

            List<String> births_death = new ArrayList<String>();
            births_death.add("No of Live Births" + "@" + childhealth[0][66]);
            births_death.add("No of Still Births (Pregnancy More than 07 Months)" + "@" + childhealth[0][67]);
            births_death.add("No of All Deaths" + "@" + childhealth[0][68]);
            births_death.add("No of neo-natal Deaths (Within 1 Week of Birth)" + "@" + childhealth[0][69]);
            births_death.add("No of Infant Deaths (Age More than 1 Week but Less than 1 Year)" + "@" + childhealth[0][70]);
            births_death.add("No of Children Deaths (Age More than 1 Year but Less than 05 Years)" + "@" + childhealth[0][71]);
            births_death.add("No of Maternal Deaths" + "@" + childhealth[0][72]);

            List<String> logistics = new ArrayList<String>();
            logistics.add("Tab. Paracetamol" + "@" + childhealth[0][73]);
            logistics.add("Syp. Paracetamol" + "@" + childhealth[0][74]);
            logistics.add("Tab. Choloroquin" + "@" + childhealth[0][75]);
            logistics.add("Syp. Choloroquin" + "@" + childhealth[0][76]);
            logistics.add("Tab. Mebendazole" + "@" + childhealth[0][77]);
            logistics.add("Syp. Pipearzine" + "@" + childhealth[0][78]);
            logistics.add("ORS" + "@" + childhealth[0][79]);
            logistics.add("Eye Ontiment" + "@" + childhealth[0][80]);
            logistics.add("Syp. Contrimexazole" + "@" + childhealth[0][81]);
            logistics.add("Iron Tab." + "@" + childhealth[0][82]);
            logistics.add("Antiseptic Lotion" + "@" + childhealth[0][83]);
            logistics.add("Benzyle Benzoate Lotion" + "@" + childhealth[0][84]);
            logistics.add("Sticking Plaster" + "@" + childhealth[0][85]);
            logistics.add("B. Complex Syp" + "@" + childhealth[0][86]);
            logistics.add("Cotton Bandages" + "@" + childhealth[0][87]);
            logistics.add("Cotton Wool" + "@" + childhealth[0][88]);
            logistics.add("Condoms" + "@" + childhealth[0][89]);
            logistics.add("Oral Pills" + "@" + childhealth[0][90]);
            logistics.add("Contraceptive Inj." + "@" + childhealth[0][91]);
            logistics.add("Others" + "@" + childhealth[0][92]);

            List<String> miscellanous = new ArrayList<String>();
            miscellanous.add("LHW Kit Bag" + "@" + childhealth[0][93]);
            miscellanous.add("Weighing Machine" + "@" + childhealth[0][94]);
            miscellanous.add("Thermometer" + "@" + childhealth[0][95]);
            miscellanous.add("Torch with Cell" + "@" + childhealth[0][96]);
            miscellanous.add("Scissors" + "@" + childhealth[0][97]);
            miscellanous.add("Syringe Cuttur" + "@" + childhealth[0][98]);
            miscellanous.add("Others" + "@" + childhealth[0][99]);

            List<String> supervision = new ArrayList<String>();
            supervision.add("No of visits by LHS" + "@" + childhealth[0][100]);
            supervision.add("No of Visit by DCO (NP)" + "@" + childhealth[0][101]);
            supervision.add("No of Visit by ADC (NP)" + "@" + childhealth[0][102]);
            supervision.add("No of Visit by FPO" + "@" + childhealth[0][103]);
            supervision.add("No # of Visit by PPIU" + "@" + childhealth[0][104]);

            listDataChild.put(listDataHeader.get(0), basicInformation); // Header, Child data
            listDataChild.put(listDataHeader.get(1), socialContacts);
            listDataChild.put(listDataHeader.get(2), childHealth);
            listDataChild.put(listDataHeader.get(3), maternal_health); // Header, Child data
            listDataChild.put(listDataHeader.get(4), family_planning);
            listDataChild.put(listDataHeader.get(5), common_aliments_a5);
            listDataChild.put(listDataHeader.get(6), common_aliments_u5);
            listDataChild.put(listDataHeader.get(7), births_death); // Header, Child data
            listDataChild.put(listDataHeader.get(8), logistics);
            listDataChild.put(listDataHeader.get(9), miscellanous);
            listDataChild.put(listDataHeader.get(10), supervision);
        }
        catch(Exception e){
            Log.d("000999", "prepareListData: "+e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Validation.this, Checklist_Validation_Button.class);
        intent.putExtra("activity_id",activity_id);
        intent.putExtra("activity_month",activity_month);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

